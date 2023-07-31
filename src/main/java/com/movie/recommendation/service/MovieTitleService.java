package com.movie.recommendation.service;

import com.movie.recommendation.model.MovieTitle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class MovieTitleService {

    private static final Logger logger = LoggerFactory.getLogger(MovieTitleService.class);
    private static final String MOVIES_FILE_PATH = "src/main/resources/data/ml-25m/movies.csv";

    public List<MovieTitle> getTitles() throws FileNotFoundException {
        File file = new File(MOVIES_FILE_PATH);
        List<MovieTitle> titles = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            String headerLine = scanner.nextLine();
            String[] headerParts = headerLine.split(",");
            int titleIndex = -1;
            for (int i = 0; i < headerParts.length; i++) {
                if (headerParts[i].equals("title")) {
                    titleIndex = i;
                    break;
                }
            }
            if (titleIndex == -1) {
                throw new RuntimeException("Title column not found in the CSV file.");
            }

            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length > titleIndex) {
                    String titleName = parts[titleIndex].trim();
                    titles.add(new MovieTitle(titleName));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found, did you download the data from https://grouplens.org/datasets/movielens/25m/ ?");
            throw e;
        } catch (Exception e) {
            logger.error("Error while reading file {}", file.getAbsolutePath(), e);
            throw e;
        }

        return titles;
    }
}
