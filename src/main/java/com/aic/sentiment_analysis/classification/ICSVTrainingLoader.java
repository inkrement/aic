package com.aic.sentiment_analysis.classification;

import com.aic.sentiment_analysis.preprocessing.PreprocessingException;

import java.io.FileNotFoundException;
import java.net.URI;
import java.util.List;

/**
 * Provides the interface to load classification training data from a CSV file.
 */
public interface ICSVTrainingLoader {
    public List<TrainingSample> load(URI uri) throws PreprocessingException, FileNotFoundException;
}
