/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat;

import com.bwc.lat.io.ExcelParser;
import com.bwc.lat.io.Storage;
import com.bwc.lat.io.dom.DiagnosisMap;
import com.bwc.lat.io.dom.Encounter;
import com.bwc.lat.io.dom.ProviderMap;
import com.bwc.lat.io.dom.Subject;
import com.oracle.util.jdbc.JDBCUtilities;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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

    private static final HashSet<String> testSubs = new HashSet<>(200);

    static {

        testSubs.add("TC_10219");
        testSubs.add("WW_10223");
        testSubs.add("KS_10238");
        testSubs.add("TC_10240");
        testSubs.add("KS_10241");
        testSubs.add("KS_10242");
        testSubs.add("DH_10244");
        testSubs.add("SR_10265");
        testSubs.add("SR_10282");
        testSubs.add("DW_10288");
        testSubs.add("JC_10435");
        testSubs.add("DH_10468");
        testSubs.add("RW_10315");
        testSubs.add("JC_10313");
        testSubs.add("JC_10334");
        testSubs.add("JC_10496");
        testSubs.add("JC_10492");
        testSubs.add("JC_10424");
        testSubs.add("DH_10342");
        testSubs.add("DH_10330");
        testSubs.add("JC_10316");
        testSubs.add("JC_10422");
        testSubs.add("CK_10452");
        testSubs.add("KS_10337");
        testSubs.add("JC_10335");
        testSubs.add("CK_10420");
        testSubs.add("DH_10323");
        testSubs.add("JC_10345");
        testSubs.add("SR_10343");
        testSubs.add("DH_10464");
        testSubs.add("JC_10461");
        testSubs.add("JG_10434");
        testSubs.add("JC_10469");
        testSubs.add("JG_10459");
        testSubs.add("SS_10457");
        testSubs.add("CK_10419");
        testSubs.add("JC_10339");
        testSubs.add("JC_10409");
        testSubs.add("JC_10451");
        testSubs.add("CK_10433");
        testSubs.add("JC_10410");
        testSubs.add("JK_10347");
        testSubs.add("JG_10460");
        testSubs.add("JC_10421");
        testSubs.add("JC_10453");
        testSubs.add("SR_10341");
        testSubs.add("TH_10415");
        testSubs.add("JC_10494");
        testSubs.add("JC_10338");
        testSubs.add("JC_10491");
        testSubs.add("JG_10324");
        testSubs.add("JC_10490");
        testSubs.add("AD_10407");
        testSubs.add("JC_10311");
        testSubs.add("SS_10458");
        testSubs.add("JB_10462");
        testSubs.add("JC_10310");
        testSubs.add("JC_10319");
        testSubs.add("JC_10487");
        testSubs.add("JC_10327");
        testSubs.add("KS_10306");
        testSubs.add("KS_10336");
        testSubs.add("DH_10466");
        testSubs.add("JC_10416");
        testSubs.add("KS_10321");
        testSubs.add("DW_10290");
        testSubs.add("JC_10344");
        testSubs.add("JC_10326");
        testSubs.add("JG_10322");
        testSubs.add("JC_10325");
        testSubs.add("JC_10488");
        testSubs.add("KS_10307");
        testSubs.add("JC_10317");
        testSubs.add("JC_10418");
        testSubs.add("GF_10408");
        testSubs.add("AD_10348");
        testSubs.add("DH_10463");
        testSubs.add("JC_10417");
        testSubs.add("JC_10414");
        testSubs.add("JC_10340");
        testSubs.add("DH_10346");
        testSubs.add("TC_10454");
        testSubs.add("DH_10465");
        testSubs.add("JG_10333");
        testSubs.add("DH_10467");
        testSubs.add("KS_10292");
        testSubs.add("TC_10489");
        testSubs.add("JG_10432");
        testSubs.add("JG_10413");
        testSubs.add("KS_10412");
        testSubs.add("JG_10423");
        testSubs.add("KS_10314");
        testSubs.add("JC_10436");
        testSubs.add("JC_10328");
        testSubs.add("JC_10318");
        testSubs.add("SS_10456");
        testSubs.add("CK_10493");
        testSubs.add("JC_10411");
        testSubs.add("JC_10320");
        testSubs.add("RW_10495");
        testSubs.add("DH_10484");
        testSubs.add("WW_10485");
        testSubs.add("SS_10455");
        testSubs.add("JC_10329");
        testSubs.add("JC_10312");
        testSubs.add("GF_10406");
        testSubs.add("JC_10726");
        testSubs.add("JC_10746");
        testSubs.add("JC_10797");
        testSubs.add("JC_10845");

    }

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
        dbUtils = new JDBCUtilities(connectionProps.getAbsolutePath());
        Class.forName(dbUtils.getDriver());
        databaseConnection = dbUtils.getConnection();
        DiagnosisMap.getInstance().setConnection(dbUtils.getConnection());
        System.out.println("Connected to Lattice database!");

        System.out.println("Clearing old loaded data...");
        Storage.clearPrevLoadedData(databaseConnection);

        System.out.println("Adding new providers...");
        ProviderMap.getInstance().addProvidersToDb(databaseConnection);

        //read information from the excel sheet
        ExcelParser ep = new ExcelParser(inputExcelFile);
        System.out.println("Parsing subjects...");
        List<Subject> subjects = ep.getSubjects();
//        int sid = subjects.stream().filter(s -> s.getAoip_id().equals("JC_0002")).findFirst().get().getSubject_id();
//        System.out.println("ID: " + sid);
//        subjects.stream()
//                .filter(s -> s.getAoip_id().equals("JC_0002"))
//                .forEach(System.out::println);
//
        System.out.println("Parsing encounters");
        List<Encounter> encounters = ep.getEncounters(subjects);
//        encounters.stream()
//                //                .filter(e -> e.getSubject_id() == sid)
//                .filter(e -> !e.getExams().isEmpty())
//                .limit(10)
//                .forEach(System.out::println);

        System.out.println("Adding subjects...");
        int insertCnt = Storage.insertSubjects(databaseConnection, subjects.stream().filter(s -> testSubs.contains(s.getAoip_id())).collect(Collectors.toList()));
        System.out.println("Added " + insertCnt + " subjects to the database...");
        Set<Integer> testSubIds = subjects.stream().filter(s -> testSubs.contains(s.getAoip_id())).map(Subject::getSubject_id).collect(Collectors.toSet());

        System.out.println("Adding encounters...");
        insertCnt = Storage.insertEncounters(databaseConnection, encounters.stream().filter(e -> testSubIds.contains(e.getSubject_id())).collect(Collectors.toList()));
        System.out.println("Added " + insertCnt + " encounters to the database...");

        System.out.println("Adding encounter exam types...");
        insertCnt = Storage.insertEncExamTypes(databaseConnection, encounters.stream().filter(e -> testSubIds.contains(e.getSubject_id())).collect(Collectors.toList()));
        System.out.println("Added " + insertCnt + " encounter exam types to the database...");

        databaseConnection.close();
        DiagnosisMap.getInstance().closeConnection2Db();
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
