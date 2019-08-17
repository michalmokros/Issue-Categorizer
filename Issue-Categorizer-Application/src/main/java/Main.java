/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    IncrementalClassifier.java
 *    Copyright (C) 2009 University of Waikato, Hamilton, New Zealand
 *
 */

//package wekaexamples.classifiers;

import entities.DataHolder;
import filtering.Cleaner;
import parsing.Parser;

import java.util.List;

/**
 * This example trains NaiveBayes incrementally on data obtained
 * from the ArffLoader.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class Main {

    /**
     * Expects an ARFF file as first argument (class attribute is assumed
     * to be the last attribute).
     *
     * @param args        the commandline arguments
     * @throws Exception  if something goes wrong
     */
    public static void main(String[] args) throws Exception {
        String csvFile = "../data/atom-atom-issues-all.csv";
        String arffFile = "../data/atom-atom-issues-all.arff";
        List<DataHolder> arffList;

        arffList = Parser.parseCsvFile(csvFile);
        Cleaner.cleanArffList(arffList);

        Parser.createArffFile(arffFile, arffList);
    }
}
