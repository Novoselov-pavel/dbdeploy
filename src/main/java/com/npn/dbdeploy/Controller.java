package com.npn.dbdeploy;

import com.npn.dbdeploy.drivers.XmlDriver;
import com.npn.dbdeploy.exception.RollbackException;
import com.npn.dbdeploy.interfaces.DbInterface;
import com.npn.dbdeploy.interfaces.InputFileInterface;
import com.npn.dbdeploy.model.DbInterfaceFabric;
import com.npn.dbdeploy.model.DbOperationItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.ArrayList;
import java.util.List;


public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private String path;


    private static final List<InputFileInterface> inputFileInterfaces = new ArrayList<>();

    static {
        inputFileInterfaces.add(new XmlDriver());
    }


    public Controller(final String[] args) {
        logger.debug("Create controller");
        String path;
        if (args==null || args.length == 0) {
            JFileChooser chooser = createDialog();
            path = openDialog(chooser);
        } else {
            path = args[0];
        }

        this.path = path;
    }

    /**Запускает выполнение файла
     *
     */
    public void execute() {
        logger.debug("execute");
        logger.info("Start executing file\t" + path);
        InputFileInterface fileInterface = getInputFileInterface(path);
        if (fileInterface == null) {
            logger.error("Interface for input file doesn't exist");
            return;
        }

        try {
            DbOperationItem operations = fileInterface.getOperation(path);
            DbInterface dbInterface = DbInterfaceFabric.getInterface(operations);
            dbInterface.executeStatements(operations.getSqlQuery());
        } catch (RollbackException e) {
            logger.error("FATAL\tSQL failed, rollback failed",e);
        } catch (Exception e) {
            logger.error("SQL failed",e);
        }
    }



    /**Возвращает реализацию интерфейса, которая может работать с данным расширением файла
     *
     * @param path путь к файлу
     * @return реализацию {@link InputFileInterface} или null если реализация не найдена
     */
    private InputFileInterface getInputFileInterface(String path) {
        logger.debug("getInputFileInterface");
        logger.info("Search interface for input file");
        return inputFileInterfaces.stream().filter(x->x.isFileHasCorrectExtension(path)).findFirst().orElse(null);
    }

    /**Конфигурация JFileChooser
     *
     * @return JFileChooser
     */
    private JFileChooser createDialog() {
        logger.debug("createDialog");
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Открыть файл");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        List<String> extensions = new ArrayList<>();
        inputFileInterfaces.forEach(x->extensions.addAll(x.getExtension()));

        FileFilter fileFilter = new FileNameExtensionFilter("Input File",extensions.toArray(new String[0]));
        chooser.setFileFilter(fileFilter);
        return chooser;
    }

    /**Открытие JFileChooser
     *
     * @param chooser JFileChooser
     * @return null при отказе пользователя
     */
    private String openDialog(JFileChooser chooser) {
        logger.debug("openDialog");
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
                logger.info("Operation cancelled by user");
                System.exit(0);
            } else {
                return openDialog(chooser);
            }
        }
        return null;
    }

}
