package ru.yandex.money.def;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

/**
 * Created by def on 13.07.14.
 */
public class Processor {
    private static Gui gui;

    public static void main(String[] args){
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process(e.getActionCommand());
            }
        };

        String testModel =
                "Название проекта\n" +
                        "" +
                        "Тест съют 1: Описание\n" +
                        "\tТест кейс 1: Описание\n" +
                        "\tТест кейс 2: Описание\n" +
                        "Тест съет 2: Описание\n" +
                        "\tТест кейс 1: Описание\n" +
                        "\tТест кейс 2: Описание";

        gui = new Gui(listener, testModel);
//        process("test", gui.getModel());
    }

    public static void process(String cmd) {
        String val = "\t";
        String testModel = gui.getModel();
        String[] testList = testModel.split("\n");


        Element root = new Element("map");
        root.setAttribute("version", "0.9.0");
        Element mainNode = new Element("node");
//        mainNode.setAttribute("CREATED", "1405199120136");
//        mainNode.setAttribute("ID", "ID_51579598");
//        mainNode.setAttribute("MODIFIED", "1405247999456");
        mainNode.setAttribute("TEXT", testList[0]);
//        mainNode.addContent(new Element("richcontent").setText(testModel).setAttribute("TYPE", "NOTE"));

        root.addContent(mainNode);
        Document doc = new Document(root);
        int depth = 1;
        HashMap <Integer, Element> lastNodes = new HashMap<Integer, Element>();
        lastNodes.put(0, mainNode);
        Element node;
        for (int i = 1; i < testList.length; i++) {
            if (testList[i].startsWith("#") || testList[i].replace(" ", "").equals("")) {
                continue;
            }
            node = new Element("node");
            String title = testList[i].replace(val, "").split(": ")[0];
            String description = null;
            if (testList[i].replace(val, "").split(": ").length == 2)   {
                description = testList[i].replace(val, "").split(": ")[1];
            }
            node.setAttribute("TEXT", title);

            if (depth == 1) {
                node.setAttribute("POSITION", "right");
            }

            if (getDepth(testList[i], val) == depth + 1) {
                depth++;
            }
            else if (getDepth(testList[i], val) < depth){
                depth = getDepth(testList[i], val);
            }

            lastNodes.get(depth - 1).addContent(node);
            lastNodes.put(depth, node);
            if (i != testList.length - 1) {
                if (getDepth(testList[i+1], val) > depth) {
                    node.addContent(new Element("attribute").setAttribute("NAME", "suite").setAttribute("VALUE", ""));
                }
                else {
                    node.addContent(new Element("attribute").setAttribute("NAME", "test").setAttribute("VALUE", ""));
                }
            }
            else {
                node.addContent(new Element("attribute").setAttribute("NAME", "test").setAttribute("VALUE", ""));
            }
            if (description != null) {
                node.addContent(new Element("richcontent").setText(description).setAttribute("TYPE", "NOTE"));
            }

        }



        StringWriter result = new StringWriter();

        try {

            XMLOutputter serializer = new XMLOutputter();
            serializer.setFormat(Format.getPrettyFormat());
            serializer.output(doc, result);
        }
        catch (IOException e) {
            System.err.println(e);
        }

        System.out.println(result.toString().replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n",""));
        String res = result.toString().replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n","");
        try {
            FileWriter fileWriter = new FileWriter("testSuiteTemplate.mm");
            fileWriter.write(res.toCharArray());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static int getDepth(String item, String val){
        int i = 1;
        while (item.startsWith(val)) {
            item = item.replaceFirst(val, "");
            i++;
        }
        return i;
    }
}
