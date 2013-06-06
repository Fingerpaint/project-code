package nl.tue.fingerpaint.server.simulator;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipInputStream;

import javax.xml.bind.DatatypeConverter;

import nl.tue.fingerpaint.client.simulator.Simulation;
import nl.tue.fingerpaint.client.simulator.SimulationResult;
import nl.tue.fingerpaint.client.simulator.SimulatorService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Implementation of the {@link SimulatorService} RPC class.
 * 
 * @author Group Fingerpaint
 */
public class SimulatorServiceImpl extends RemoteServiceServlet implements
		SimulatorService {

	/**
	 * Random serial version uid
	 */
	private static final long serialVersionUID = 3842884820635044977L;

	@Override
	public SimulationResult simulate(Simulation request) {

		int numVectorResults = request.calculatesIntermediateVectors() ? request
				.getProtocolRuns() : 1;

		double[] segregationPoints = new double[request.getProtocolRuns()];

		double segregationPoint = 0;
		System.out.println(request.getConcentrationVector2()[0]);
		Logger.getLogger("").log(Level.INFO, "" +request.getConcentrationVector2()[0]);
		String zippedVector = request.getConcentrationVector();
		// TODO: Unzip zippedVector and store in intVector
		
		double[] doubleVector = doubleArrayFromString(unzip(zippedVector));

		int[][] vectorResults = new int[numVectorResults][doubleVector.length];

		for (int i = 0; i < request.getProtocolRuns(); i++) {
			for (int j = 0; j < request.getProtocol().getProgramSize(); j++) {
				segregationPoint = NativeCommunicator.getInstance().simulate(
						request.getProtocol().getGeometry(),
						request.getMixer(), doubleVector,
						request.getProtocol().getStep(j).getStepSize(),
						request.getProtocol().getStep(j).getName());
			}
			segregationPoints[i] = segregationPoint;
			if (request.calculatesIntermediateVectors()) {
				vectorResults[i] = toIntVector(doubleVector.clone());
			}
		}
		if (!request.calculatesIntermediateVectors()) {
			vectorResults[0] = toIntVector(doubleVector.clone());
		}

		return new SimulationResult(vectorResults, segregationPoints);
	}

	private int[] toIntVector(double[] doubleVector) {
		int[] result = new int[doubleVector.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (int) doubleVector[i] * 255;
		}
		return result;
	}

	private String unzip(String zippedString) {
		try {
			byte[] bytes = DatatypeConverter.parseBase64Binary(zippedString);
			ByteArrayInputStream byteIS = new ByteArrayInputStream(bytes);
			ZipInputStream zipIS = new ZipInputStream(byteIS);
			zipIS.getNextEntry();

			InputStreamReader dataIS = new InputStreamReader(zipIS);
			BufferedReader br = new BufferedReader(dataIS);
			StringBuilder sb = new StringBuilder();
			String append = "";
			while (append != null) {
				sb.append(append);
				append = br.readLine();
			}
			return sb.toString();
		} catch (IOException e) {
			throw new Error(e);
		}
	}
	
	
	public static double[] doubleArrayFromString(String jsonIntArray) {
		String s = jsonIntArray.substring(1, jsonIntArray.length() - 1);
		String[] ints = s.split(",");
		double[] result = new double[ints.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = Double.parseDouble(ints[i]) / 255;
		}
		return result;
	}

}