package com.obomprogramador.tools.jqana.parsers;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ConstantMethodref;
import org.apache.bcel.classfile.ConstantNameAndType;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.DescendingVisitor;
import org.apache.bcel.classfile.EmptyVisitor;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.model.AbstractMetricParser;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.Measurement.MEASUREMENT_TYPE;
import com.obomprogramador.tools.jqana.model.defaultimpl.MetricValue;


public class RfcBcelParser extends AbstractMetricParser {

	public RfcBcelParser(Measurement packageMeasurement, Context context) {
		super(packageMeasurement, context, "metric.rfc.name");
	}

	@Override
	public Measurement parse(String compiledClassPath, String sourceCode) {
		this.measurement = new Measurement(); // Class name will be set inside listener.
		this.measurement.setType(MEASUREMENT_TYPE.CLASS_MEASUREMENT);
		this.metricValue = new MetricValue();
		this.metricValue.setName(this.metric.getMetricName());
		this.measurement.getMetricValues().add(this.metricValue);
		try {
			ClassParser cParser = new ClassParser(compiledClassPath);
			JavaClass javaClass = cParser.parse();
			RfcVisitor visitor = new RfcVisitor(javaClass);
			DescendingVisitor classWalker = new DescendingVisitor(javaClass, visitor);
			classWalker.visit();
			
			updatePackageMeasurement();
			
		} catch (Exception e) {
			context.getErrors().push(e.getMessage());
			logger.error(e.getMessage());
		}
		return measurement;
	}

	
	class RfcVisitor extends EmptyVisitor {
		private JavaClass javaClass;
		private MetricValue mv;
		RfcVisitor(JavaClass javaClass) {
			this.javaClass = javaClass;
			mv = measurement.getMetricValue(context.getBundle().getString("metric.rfc.name"));
		}
		
		@Override
		public void visitConstantMethodref(ConstantMethodref obj) {
			ConstantPool cp = javaClass.getConstantPool();
		    String className = obj.getClass(cp);
		    ConstantNameAndType constant = (ConstantNameAndType) cp.getConstant(obj.getNameAndTypeIndex());
		    mv.setValue(mv.getValue() + 1);
		    logger.debug("Method call: " + className + " : " + constant.getName(cp) + " : " + constant.getSignature(cp));
			
		}
		
		@Override
		public void visitMethod(Method obj) {
			mv.setValue(mv.getValue() + 1);
			logger.debug("Method: " + obj.getName() + " : " + obj.getSignature());
		}

	}
	

}
