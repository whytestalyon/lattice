/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat;

import com.bwc.lat.io.ExcelParser;
import com.bwc.lat.io.dom.Subject;
import com.oracle.util.jdbc.JDBCUtilities;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author melissadiederichs
 */
public class LatticeLoader {

    private static JDBCUtilities dbUtils;
    private static File connectionProps = null;
    private static File inputExcelFile = null;
    private static Connection databaseConnection;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, InvalidFormatException {
        //check the supplied command line parameters and init fields
        checkInputAndInitialize(args);
        
        //connect to the database using the connection informatyion
        //specified in the properties file
//        dbUtils = new JDBCUtilities(connectionProps.getAbsolutePath());
//        Class.forName(dbUtils.getDriver());
//        databaseConnection = dbUtils.getConnection();
//        System.out.println("Connected to Lattice database!");
        
        //read information from the excel sheet
        ExcelParser ep = new ExcelParser(inputExcelFile);
        List<Subject> subjects = ep.getSubjects();
    }

    /**
     * Load information from the configuration file and override it with any
     * input from the command line supplied parameters.
     *
     * @param args
     */
    private static void checkInputAndInitialize(String[] args) {
        String header = "This loader is responsible for parsing patient information from and Excel sheet and loading said data "
                + "into the Lattice database.";
        String footer = "Contact wilkb777@gmail.com with issues, or if you want to buy me a beer.";
        //CLI initialization and integration.
        Option xml = OptionBuilder.withArgName("File Path")
                .hasArg()
                .withDescription("The path to the excel file conatining the patient data to be loaded.")
                .create("i");
        Option db = OptionBuilder.withArgName("File Path")
                .hasArg()
                .withDescription("Specifiy the path to the XML file that contains the connection information to the LATTICE database.")
                .create("db");
        Option help = new Option("help", "Print this help message");
        Options options = new Options();
        options.addOption(xml);
        options.addOption(db);
        options.addOption(help);

        // create the parser and check inputs
        CommandLineParser parser = new GnuParser();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);
            //check if help needs to be displayed
            if (line.hasOption("help")) {
                // automatically generate the help statement
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("LatticeLoader [OPTIONS]...", header, options, footer);
                System.exit(1);
            }

            if (!line.hasOption("db") || !line.hasOption("i")) {
                throw new ParseException("Missing database config XML file path or input Excel file path.");
            }

            //grab Excel file path
            inputExcelFile = new File(line.getOptionValue("i"));
            if (inputExcelFile.exists()) {
                if (!inputExcelFile.isFile()) {
                    throw new ParseException("Supplied input Excel file is not a file!");
                }
            } else {
                throw new ParseException("Supplied input Excel file does not exist.");
            }

            //parse and verify that the supplied db file is correct
            connectionProps = new File(line.getOptionValue("db"));
            if (connectionProps.exists()) {
                if (!connectionProps.isFile()) {
                    throw new ParseException("Supplied database config XML file is not a file!");
                }
            } else {
                throw new ParseException("Supplied database config XML file does not exist.");
            }
        } catch (ParseException exp) {
            // oops, something went wrong
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
            // automatically generate the help statement
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("LatticeLoader [OPTIONS]...", header, options, footer);
            System.exit(1);
        }
    }

}
