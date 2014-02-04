package com.obomprogramador.tools.jqana.parsers;

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

/**
 * This is a BCEL parser class, used to analyze RFC metric.
 * To avoid compiler optimizations, 
 * I would have rather used only AST parsers (based on source code), 
 * but in the case of the metric RFC, the resulting parser 
 * would be very complex.
 * 
 * @author Cleuton Sampaio.
 *
 */
public class RfcBcelParser extends AbstractMetricParser {

    /**
     * Constructor with parameters.
     * @param packageMeasurement Measurement the package's measurement.
     * @param context Context the context in use.
     */
    public RfcBcelParser(Measurement packageMeasurement, Context context) {
        super(packageMeasurement, context, "metric.rfc.name");
        this.context = context;
    }

    @Override
    public Measurement parse(String compiledClassPath, String sourceCode) {
        this.measurement = new Measurement();
        this.measurement.setType(MEASUREMENT_TYPE.CLASS_MEASUREMENT);
        this.metricValue = new MetricValue();
        this.metricValue.setName(this.metric.getMetricName());
        this.measurement.getMetricValues().add(this.metricValue);
        try {
            ClassParser cParser = new ClassParser(compiledClassPath);
            JavaClass javaClass = cParser.parse();
            this.measurement.setName(getClassNameFromJavaClass(javaClass));
            RfcVisitor visitor = new RfcVisitor(javaClass);
            DescendingVisitor classWalker = new DescendingVisitor(javaClass,
                    visitor);
            classWalker.visit();
            MetricValue mv = this.measurement.getMetricValue(this
                    .getParserName());
            int rfcLimit = Integer.parseInt(context.getBundle().getString(
                    "metric.rfc.limit"));
            if (mv.getValue() > rfcLimit) {
                mv.setViolated(true);
            }
            updatePackageMeasurement();

        } catch (Exception e) {
            context.getErrors().push(e.getMessage());
            logger.error(e.getMessage());
        }
        return measurement;
    }

    protected String getClassNameFromJavaClass(JavaClass javaClass) {
        String className = javaClass.getClassName();
        int pos = className.lastIndexOf('.');
        if (pos >= 0) {
            className = className.substring(pos + 1);
        }
        return className;
    }

    /**
     * This is a Visitor class, used by BCEL framework.
     * @author Cleuton Sampaio
     *
     */
    class RfcVisitor extends EmptyVisitor {
        private JavaClass javaClass;
        private MetricValue mv;

        /**
         * Constructor with arguments.
         * @param javaClass JavaClass the class to visit.
         */
        RfcVisitor(JavaClass javaClass) {
            this.javaClass = javaClass;
            mv = measurement.getMetricValue(context.getBundle().getString(
                    "metric.rfc.name"));
        }

        @Override
        public void visitConstantMethodref(ConstantMethodref obj) {
            ConstantPool cp = javaClass.getConstantPool();
            String className = obj.getClass(cp);
            ConstantNameAndType constant = (ConstantNameAndType) cp
                    .getConstant(obj.getNameAndTypeIndex());
            mv.setValue(mv.getValue() + 1);
            logger.debug("Method call: " + className + " : "
                    + constant.getName(cp) + " : " + constant.getSignature(cp));

        }

        @Override
        public void visitMethod(Method obj) {
            mv.setValue(mv.getValue() + 1);
            logger.debug("Method: " + obj.getName() + " : "
                    + obj.getSignature());
        }

    }

}
