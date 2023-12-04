package edu.augustana;

import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

import java.util.ArrayList;

public class Print {


    void printAnchorPane(VBox printablePage) {
        Printer printer = Printer.getDefaultPrinter();
        PrinterJob printerJob = PrinterJob.createPrinterJob(printer);

        if (printerJob != null && printerJob.showPrintDialog(null)) {
            // Get the page layout for A4 paper
            PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();
            double scaleX = pageLayout.getPrintableWidth() / printablePage.getBoundsInParent().getWidth();
            double scaleY = pageLayout.getPrintableHeight() / printablePage.getBoundsInParent().getHeight();
            printablePage.getTransforms().add(new Scale(scaleX, scaleY));

            boolean success = printerJob.printPage(printablePage);

            if (success) {
                // End the print job
                printerJob.endJob();
            }
        }
    }

}