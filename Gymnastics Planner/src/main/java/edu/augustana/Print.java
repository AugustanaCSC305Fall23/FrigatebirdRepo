package edu.augustana;

import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.layout.VBox;

public class Print {


   void printAnchorPane( VBox printablePage) {
        Printer printer = Printer.getDefaultPrinter();
        PrinterJob printerJob = PrinterJob.createPrinterJob(printer);

        if (printerJob != null && printerJob.showPrintDialog(null)) {
            boolean success = printerJob.printPage(printablePage);

            if (success) {
                printerJob.endJob();
            }

        }
    }


}
