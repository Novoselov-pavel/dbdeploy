package com.npn.updater;

import com.npn.updater.drivers.XmlDriver;
import com.npn.updater.interfaces.DbInterface;
import com.npn.updater.interfaces.InputFileInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.ArrayList;
import java.util.List;


public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private final DbInterface dbInterface;
    private final InputFileInterface inputFileInterface;

    private static final List<InputFileInterface> inputFileInterfaces = new ArrayList<>();

    static {
        inputFileInterfaces.add(new XmlDriver());
    }


    public Controller(String[] args) {
        String path;

        if (args==null || args.length == 0) {
            JFileChooser chooser = createDialog();
            path = openDialog(chooser);
        } else {

        }

        ///TODO
        dbInterface = null;
        inputFileInterface = null;
    }

    /**Конфигурация JFileChooser
     *
     * @return JFileChooser
     */
    private JFileChooser createDialog() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Открыть файл");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        List<String> extensions = new ArrayList<>();
        inputFileInterfaces.forEach(x->extensions.addAll(x.getExtension()));

        FileFilter fileFilter = new FileNameExtensionFilter("Input File",extensions.toArray(new String[extensions.size()]));
        chooser.setFileFilter(fileFilter);
        return chooser;
    }

    /**Открытие JFileChooser
     *
     * @param chooser JFileChooser
     * @return null при отказе пользователя
     */
    private String openDialog(JFileChooser chooser) {

        int result = chooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().toPath().toAbsolutePath().toString();

        } else {
            int i = JOptionPane.showConfirmDialog(null,
                    "Файл не выбран, завершить?",
                    "Внимание",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (i==JOptionPane.YES_OPTION) {
                return null;
            } else {
                return openDialog(chooser);
            }
        }
    }

}
