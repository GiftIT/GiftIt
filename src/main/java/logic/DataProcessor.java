package logic;

import net.sf.javaml.core.Instance;

public interface DataProcessor {
    Object classify(Instance instance);
}
