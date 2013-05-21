// Multi-Threaded web server using posix pthreads
// BK Turley 2011
 
//this simple web server is capible of serving simple html, jpg, gif & text files
 
//----- Include files ---------------------------------------------------------
#include <stdio.h>          // for printf()
#include <stdlib.h>         // for exit()
#include <string.h>         // for strcpy(),strerror() and strlen()
#include <fcntl.h>          // for file i/o constants
#include <sys/stat.h>       // for file i/o constants
#include <errno.h>
 
/* FOR BSD UNIX/LINUX  ---------------------------------------------------- */
#include <sys/types.h>      //  
#include <netinet/in.h>     //  
#include <sys/socket.h>     // for socket system calls 
#include <arpa/inet.h>      // for socket system calls (bind)
#include <sched.h>  
#include <pthread.h>        /* P-thread implementation        */   
#include <signal.h>         /* for signal                     */
#include <semaphore.h>      /* for p-thread semaphores        */
/* ------------------------------------------------------------------------ */ 
 
#include <unistd.h>
#include <json/json.h>
#include <json/json_object.h>
#include <json/json_tokener.h>
#include <stdint.h>
#include <stdio.h>

//----- HTTP response messages ----------------------------------------------
#define OK_IMAGE    "HTTP/1.0 200 OK\nContent-Type:image/gif\n\n"
#define OK_TEXT     "HTTP/1.0 200 OK\nContent-Type:text/html\n\n"
#define NOTOK_404   "HTTP/1.0 404 Not Found\nContent-Type:text/html\n\n"
#define MESS_404    "<html><body><h1>FILE NOT FOUND</h1></body></html>"
 
//----- Defines -------------------------------------------------------------
#define BUF_SIZE            1024     // buffer size in bytes
#define PORT_NUM            6110     // Port number for a Web server (TCP 5080)
#define PEND_CONNECTIONS     100     // pending connections to hold
#define NTHREADS 5                     /* Number of child threads        */
#define NUM_LOOPS  10                  /* Number of local loops          */
#define SCHED_INTVL 5                  /* thread scheduling interval     */
#define HIGHPRIORITY 10
 
/* global variables ---------------------------------------------------- */
sem_t thread_sem[NTHREADS]; 
int   next_thread; 
int   can_run;
int   i_stopped[NTHREADS];
 
unsigned int    client_s;               // Client socket descriptor
 
void json_parse(json_object * jobj) {
 enum json_type type;

 type = json_object_get_type(jobj);
 switch (type) {
 case json_type_string:
	 printf("type: json_type_string, ");
	 printf("value: %s\n", json_object_get_string(jobj));
	 break;
 case json_type_object:
	 printf("type: json_type_object: contents:\n");
	 json_object_object_foreach(jobj, key, val) {
		 printf("key: %s\n", key);
		 json_parse(val);
	 }
	 break;
 case json_type_boolean:
	 printf("type: json_type_boolean, ");
	 printf("value: %d\n", json_object_get_boolean(jobj));
	 break;
 case json_type_double:
 	 printf("type: json_type_double, ");
 	 printf("value: %f\n", json_object_get_double(jobj));
 	 break;
 case json_type_int:
 	 printf("type: json_type_int, ");
 	 printf("value: %d\n", json_object_get_int(jobj));
 	 break;
 case json_type_null:
  	 printf("type: json_type_null, ");
  	 printf("value: NULL");
  	 break;
 case json_type_array:
 	 printf("type: json_type_array, ");
 	 int i;
 	 array_list *array = json_object_get_array(jobj);
 	 int number = json_object_array_length(jobj);
 	 printf("count %d\n", number);
 	 for (i = 0; i < number; i++) {
 		 printf("key %d\n", i);
 		 json_parse(json_object_array_get_idx(jobj, i));
 	 }
 	 break;
  }
  }
 
/* Child thread implementation ----------------------------------------- */
void *my_thread(void * arg) {
	unsigned int   client_socket;         //copy socket
	int        content_length;
	char buff[10];
	int            num_read;
	json_object *rootNode;
	json_tokener *tokener;
	int cont = 1;

	client_socket = *(unsigned int *)arg;        // copy the socket

	while (cont) {
		printf("\n\n\n new iteration \n\n\n");

		tokener = json_tokener_new();
		do {
			num_read = read(client_socket, buff, sizeof(buff));
			if (num_read <= 0) {
				printf("\n\n\n closing... \n\n\n");
				close(client_s); // close the client connection
				cont = 0;
				pthread_exit(NULL);
			} else {
				rootNode = json_tokener_parse_ex(tokener, buff, num_read);
				printf("one read : %d\n", num_read);
				printf("%d\n", tokener->err);
			}
		} while (tokener->err == json_tokener_continue && cont);

		if (tokener->err != json_tokener_success) {
			printf("json tokener error\n");
		}

		printf("Content read\n");

		json_parse(rootNode);

		printf("parse ready");
	}

	return NULL;
}
 
//===== Main program ========================================================
int main(void)
{
  /* local variables for socket connection -------------------------------- */
  unsigned int          server_s;               // Server socket descriptor
  struct sockaddr_in    server_addr;            // Server Internet address
  //unsigned int            client_s;           // Client socket descriptor
  struct sockaddr_in    client_addr;            // Client Internet address
  struct in_addr        client_ip_addr;         // Client IP address
  int                   addr_len;               // Internet address length
 
  unsigned int          ids;                    // holds thread args
  pthread_attr_t        attr;                   //  pthread attributes
  pthread_t             threads;                // Thread ID (used by OS)
 
  /* required for eclipse to flush the prints to stdout ------------------- */
  setvbuf (stdout, NULL, _IONBF, 0);
  /* create a new socket -------------------------------------------------- */
  server_s = socket(AF_INET, SOCK_STREAM, 0);
 
  /* fill-in address information, and then bind it ------------------------ */
  server_addr.sin_family = AF_INET;
  server_addr.sin_port = htons(PORT_NUM);
  server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
  bind(server_s, (struct sockaddr *)&server_addr, sizeof(server_addr));
 
  /* Listen for connections and then accept ------------------------------- */
  listen(server_s, PEND_CONNECTIONS);
 
  /* the web server main loop ============================================= */
  pthread_attr_init(&attr);
  while(TRUE)
  {
    printf("server ready\n");
 
    /* wait for the next client to arrive -------------- */
    addr_len = sizeof(client_addr);
    client_s = accept(server_s, (struct sockaddr *)&client_addr, &addr_len);
    if (client_s == FALSE)
    {
      printf("ERROR - Unable to create socket \n");
      exit(FALSE);
    } else {
    	printf("new request arriving\n");

        /* Create a child thread --------------------------------------- */
        ids = client_s;
        pthread_create (                    /* Create a child thread        */
                   &threads,                /* Thread ID (system assigned)  */
                   &attr,                   /* Default thread attributes    */
                   my_thread,               /* Thread routine               */
                   &ids);                   /* Arguments to be passed       */
 
    }
  }
 
  /* To make sure this "main" returns an integer --- */
  close (server_s);  // close the primary socket
  return (TRUE);        // return code from "main"
}
