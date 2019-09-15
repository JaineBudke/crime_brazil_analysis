package jmeterTests;


import java.io.FileNotFoundException;
import java.io.Serializable;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import CrimeAnalysis.Main;
import CrimeAnalysis.Process;


public class JMeterTest extends AbstractJavaSamplerClient implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Override public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
	
		String var1 = javaSamplerContext.getParameter("var1");
		String var2 = javaSamplerContext.getParameter("var2");
		String var3 = javaSamplerContext.getParameter("var3");

		
		
		SampleResult result = new SampleResult();
		result.sampleStart();
		result.setSampleLabel("Test Sample");
		
		Main main = new Main();
		Process proc = null;
		try {
			proc = new Process();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		if( Main.makeAnalysis( 4, proc, var1, var2, var3) == "INSEGURO" ) {
			result.sampleEnd();
			result.setResponseCode("200");
			result.setResponseMessage("OK");
			result.setSuccessful(true);
		} else {
			result.sampleEnd();
			result.setResponseCode("500");
			result.setResponseMessage("NOK");
			result.setSuccessful(false);
		}
		return result; 
	}
		
	@Override public Arguments getDefaultParameters() {
		Arguments defaultParameters = new Arguments();
		defaultParameters.addArgument("var1","BRANCA");
		defaultParameters.addArgument("var2","F");
		defaultParameters.addArgument("var3","Tarde");
		return defaultParameters; 
	} 
	
}
