package edu.augustana;

import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.nio.channels.Pipe;

public class Print {


    VBox printablePage;



   void printAnchorPane(VBox printablePage) {

       this.printablePage = printablePage;

        Printer printer = Printer.getDefaultPrinter();
        PrinterJob printerJob = PrinterJob.createPrinterJob(printer);

        if (printerJob != null && printerJob.showPrintDialog(null)) {
            boolean success = printerJob.printPage(printablePage);

            if (success) {
                printerJob.endJob();
            }

        }

    }


    void getStringFromPrintData(){




    }

}
