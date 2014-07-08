/*
 * This file is part of KNBTE
 *
 * Copyright (C) 2011-2013 Keyle
 * KNBTE is licensed under the GNU Lesser General Public License.
 *
 * KNBTE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * KNBTE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package de.keyle.knbte;

import com.google.common.collect.BiMap;
import de.keyle.knbt.*;
import de.keyle.knbte.tags.*;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Gui {
    private JTree tagTree;
    private JButton byteAddButton;
    private JButton listAddButton;
    private JButton shortAddButton;
    private JButton intAddButton;
    private JButton longAddButton;
    private JButton floatAddButton;
    private JButton doubleAddButton;
    private JButton byteArrayAddButton;
    private JButton intArrayAddButton;
    private JButton stringAddButton;
    private JButton compoundAddButton;
    private JPanel mainPanel;
    private JButton openFileButton;
    private JButton newFileButton;
    private JButton saveButton;
    private JButton changeNameButton;
    private JButton editValueButton;
    private JButton deleteTagButton;

    JPopupMenu listAddButtonMenu;

    public File openedFile = null;

    final JFileChooser fc = new JFileChooser();

    final ImageIcon BYTE_ICON = new ImageIcon(ClassLoader.getSystemResource("icons/tag/byte.png"));
    final ImageIcon BYTE_ARRAY_ICON = new ImageIcon(ClassLoader.getSystemResource("icons/tag/byte_array.png"));
    final ImageIcon COMPOUND_ICON = new ImageIcon(ClassLoader.getSystemResource("icons/tag/compound.png"));
    final ImageIcon DOUBLE_ICON = new ImageIcon(ClassLoader.getSystemResource("icons/tag/double.png"));
    final ImageIcon FLOAT_ICON = new ImageIcon(ClassLoader.getSystemResource("icons/tag/float.png"));
    final ImageIcon INT_ICON = new ImageIcon(ClassLoader.getSystemResource("icons/tag/int.png"));
    final ImageIcon INT_ARRAY_ICON = new ImageIcon(ClassLoader.getSystemResource("icons/tag/int_array.png"));
    final ImageIcon LIST_ICON = new ImageIcon(ClassLoader.getSystemResource("icons/tag/list.png"));
    final ImageIcon LONG_ICON = new ImageIcon(ClassLoader.getSystemResource("icons/tag/long.png"));
    final ImageIcon SHORT_ICON = new ImageIcon(ClassLoader.getSystemResource("icons/tag/short.png"));
    final ImageIcon STRING_ICON = new ImageIcon(ClassLoader.getSystemResource("icons/tag/string.png"));

    final ImageIcon NEW_ICON = new ImageIcon(ClassLoader.getSystemResource("icons/add.png"));
    final ImageIcon OPEN_ICON = new ImageIcon(ClassLoader.getSystemResource("icons/open.png"));
    final ImageIcon SAVE_ICON = new ImageIcon(ClassLoader.getSystemResource("icons/save.png"));
    final ImageIcon SAVE_AS_ICON = new ImageIcon(ClassLoader.getSystemResource("icons/save_as.png"));
    final ImageIcon EXIT_ICON = new ImageIcon(ClassLoader.getSystemResource("icons/close.png"));

    public Gui(JFrame skilltreeCreatorFrame, File argFile) {
        if (argFile != null) {
            openFile(argFile);
        }

        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        menuBar.add(menuFile);

        JMenuItem newMenuItem = new JMenuItem("New", NEW_ICON);
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke('N', KeyEvent.CTRL_DOWN_MASK));
        menuFile.add(newMenuItem);
        JMenuItem openMenuItem = new JMenuItem("Open...", OPEN_ICON);
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke('O', KeyEvent.CTRL_DOWN_MASK));
        menuFile.add(openMenuItem);
        JMenuItem saveMenuItem = new JMenuItem("Save...", SAVE_ICON);
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_DOWN_MASK));
        menuFile.add(saveMenuItem);
        JMenuItem saveAsMenuItem = new JMenuItem("Save as...", SAVE_AS_ICON);
        saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_DOWN_MASK + KeyEvent.ALT_DOWN_MASK));
        menuFile.add(saveAsMenuItem);
        menuFile.add(new JSeparator());
        JMenuItem exitMenuItem = new JMenuItem("Exit", EXIT_ICON);
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK));
        menuFile.add(exitMenuItem);

        skilltreeCreatorFrame.setJMenuBar(menuBar);

        listAddButtonMenu = new JPopupMenu();

        JMenuItem byteMenuItem = new JMenuItem("Byte", BYTE_ICON);
        listAddButtonMenu.add(byteMenuItem);

        JMenuItem shortMenuItem = new JMenuItem("Short", SHORT_ICON);
        listAddButtonMenu.add(shortMenuItem);

        JMenuItem intMenuItem = new JMenuItem("Integer", INT_ICON);
        listAddButtonMenu.add(intMenuItem);

        JMenuItem longMenuItem = new JMenuItem("Long", LONG_ICON);
        listAddButtonMenu.add(longMenuItem);

        JMenuItem floatMenuItem = new JMenuItem("Float", FLOAT_ICON);
        listAddButtonMenu.add(floatMenuItem);

        JMenuItem doubleMenuItem = new JMenuItem("Double", DOUBLE_ICON);
        listAddButtonMenu.add(doubleMenuItem);

        JMenuItem byteArrayMenuItem = new JMenuItem("Byte Array", BYTE_ARRAY_ICON);
        listAddButtonMenu.add(byteArrayMenuItem);

        JMenuItem intArrayMenuItem = new JMenuItem("Integer Array", INT_ARRAY_ICON);
        listAddButtonMenu.add(intArrayMenuItem);

        JMenuItem stringMenuItem = new JMenuItem("String", STRING_ICON);
        listAddButtonMenu.add(stringMenuItem);

        JMenuItem compoundMenuItem = new JMenuItem("Compound", COMPOUND_ICON);
        listAddButtonMenu.add(compoundMenuItem);

        JMenuItem listMenuItem = new JMenuItem("List", LIST_ICON);
        listAddButtonMenu.add(listMenuItem);

        ActionListener saveListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                File fileToSaveTo = null;
                if (openedFile != null) {
                    fileToSaveTo = openedFile;
                } else {
                    int returnVal = fc.showSaveDialog(openFileButton);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        fileToSaveTo = fc.getSelectedFile();
                    } else if (returnVal == JFileChooser.CANCEL_OPTION) {
                        return;
                    }
                }
                if (fileToSaveTo != null) {
                    TagCompound compound = (TagCompound) ((TagCompoundNode) tagTree.getModel().getRoot()).toTag();
                    try {
                        TagStream.writeTag(compound, new FileOutputStream(fileToSaveTo), true);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        };
        ActionListener openListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

                int returnVal = fc.showOpenDialog(openFileButton);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    System.out.println("Opening: " + file.getName());

                    openFile(file);
                }
            }
        };
        ActionListener newListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tagTree.setModel(new DefaultTreeModel(new TagCompoundNode(new TagCompound())));
                openedFile = null;
            }
        };

        listAddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listAddButtonMenu.show(listAddButton, listAddButton.getWidth() - listAddButtonMenu.getWidth(), listAddButton.getHeight());
            }
        });
        openFileButton.addActionListener(openListener);
        openMenuItem.addActionListener(openListener);
        saveButton.addActionListener(saveListener);
        saveMenuItem.addActionListener(saveListener);
        newFileButton.addActionListener(newListener);
        newMenuItem.addActionListener(newListener);
        saveAsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                File fileToSaveTo = null;
                int returnVal = fc.showSaveDialog(openFileButton);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    fileToSaveTo = fc.getSelectedFile();
                } else if (returnVal == JFileChooser.CANCEL_OPTION) {
                    return;
                }
                if (fileToSaveTo != null) {
                    TagCompound compound = (TagCompound) ((TagCompoundNode) tagTree.getModel().getRoot()).toTag();
                    try {
                        TagStream.writeTag(compound, new FileOutputStream(fileToSaveTo), true);
                        openedFile = fileToSaveTo;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        changeNameButton.addActionListener(new ActionListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagCompoundNode parent = (TagCompoundNode) node.getParent();
                String key = parent.getKeyByNode(node);
                JTextField textField = new JTextField();
                textField.setText(key);
                int result = JOptionPane.showConfirmDialog(null, textField, "Enter a new Key", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String newKey = textField.getText();
                    if (parent.getNodeByKey(newKey) == null) {
                        BiMap<String, TagBaseNode> key2Node = (BiMap<String, TagBaseNode>) parent.getData();
                        key2Node.remove(key);
                        key2Node.put(newKey, node);
                        node.updateUserObject();
                        tagTree.updateUI();
                    }
                }
            }
        });
        editValueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                getSelectedNode().editValue();
                tagTree.updateUI();
            }
        });
        deleteTagButton.addActionListener(new ActionListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagBaseNode parent = (TagBaseNode) node.getParent();
                parent.remove(node);
                parent.updateUserObject();
                tagTree.updateUI();
            }
        });
        tagTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent event) {
                TagBaseNode selectedNode = getSelectedNode();
                if (selectedNode != null) {
                    deleteTagButton.setEnabled(true);

                    if (selectedNode.getParent() != null) {
                        if (selectedNode.getParent() instanceof TagCompoundNode) {
                            changeNameButton.setEnabled(true);
                        } else {
                            changeNameButton.setEnabled(false);
                        }
                    } else {
                        changeNameButton.setEnabled(false);
                        deleteTagButton.setEnabled(false);
                    }

                    if (selectedNode instanceof TagCompoundNode) {
                        editValueButton.setEnabled(false);
                        byteAddButton.setEnabled(true);
                        shortAddButton.setEnabled(true);
                        intAddButton.setEnabled(true);
                        longAddButton.setEnabled(true);
                        floatAddButton.setEnabled(true);
                        doubleAddButton.setEnabled(true);
                        byteArrayAddButton.setEnabled(true);
                        intArrayAddButton.setEnabled(true);
                        stringAddButton.setEnabled(true);
                        compoundAddButton.setEnabled(true);
                        listAddButton.setEnabled(true);
                    } else if (selectedNode instanceof TagListNode) {
                        TagType type = ((TagListNode) selectedNode).getListTagType();
                        editValueButton.setEnabled(false);
                        byteAddButton.setEnabled(type == TagType.Byte);
                        shortAddButton.setEnabled(type == TagType.Short);
                        intAddButton.setEnabled(type == TagType.Int);
                        longAddButton.setEnabled(type == TagType.Long);
                        floatAddButton.setEnabled(type == TagType.Float);
                        doubleAddButton.setEnabled(type == TagType.Double);
                        byteArrayAddButton.setEnabled(type == TagType.Byte_Array);
                        intArrayAddButton.setEnabled(type == TagType.Int_Array);
                        stringAddButton.setEnabled(type == TagType.String);
                        compoundAddButton.setEnabled(type == TagType.Compound);
                        listAddButton.setEnabled(type == TagType.List);
                    } else {
                        editValueButton.setEnabled(true);
                        byteAddButton.setEnabled(false);
                        shortAddButton.setEnabled(false);
                        intAddButton.setEnabled(false);
                        longAddButton.setEnabled(false);
                        floatAddButton.setEnabled(false);
                        doubleAddButton.setEnabled(false);
                        byteArrayAddButton.setEnabled(false);
                        intArrayAddButton.setEnabled(false);
                        stringAddButton.setEnabled(false);
                        compoundAddButton.setEnabled(false);
                        listAddButton.setEnabled(false);
                    }
                } else {
                    deleteTagButton.setEnabled(false);
                    editValueButton.setEnabled(false);
                    changeNameButton.setEnabled(false);
                    byteAddButton.setEnabled(false);
                    shortAddButton.setEnabled(false);
                    intAddButton.setEnabled(false);
                    longAddButton.setEnabled(false);
                    floatAddButton.setEnabled(false);
                    doubleAddButton.setEnabled(false);
                    byteArrayAddButton.setEnabled(false);
                    intArrayAddButton.setEnabled(false);
                    stringAddButton.setEnabled(false);
                    compoundAddButton.setEnabled(false);
                    listAddButton.setEnabled(false);
                }
            }
        });
        byteAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagBaseNode newNode = new TagByteNode(new TagByte(false));
                addToNode(node, newNode);
            }
        });
        shortAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagBaseNode newNode = new TagShortNode(new TagShort((short) 0));
                addToNode(node, newNode);
            }
        });
        intAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagBaseNode newNode = new TagIntNode(new TagInt(0));
                addToNode(node, newNode);
            }
        });
        longAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagBaseNode newNode = new TagLongNode(new TagLong(0L));
                addToNode(node, newNode);
            }
        });
        floatAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagBaseNode newNode = new TagFloatNode(new TagFloat(0F));
                addToNode(node, newNode);
            }
        });
        doubleAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagBaseNode newNode = new TagDoubleNode(new TagDouble(0D));
                addToNode(node, newNode);
            }
        });
        byteArrayAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagBaseNode newNode = new TagByteArrayNode(new TagByteArray(new byte[0]));
                addToNode(node, newNode);
            }
        });
        intArrayAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagBaseNode newNode = new TagIntArrayNode(new TagIntArray(new int[0]));
                addToNode(node, newNode);
            }
        });
        stringAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagBaseNode newNode = new TagStringNode(new TagString(""));
                addToNode(node, newNode);
            }
        });
        compoundAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagBaseNode newNode = new TagCompoundNode(new TagCompound());
                addToNode(node, newNode);
            }
        });
        byteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagListNode newNode = new TagListNode(new TagList());
                newNode.type = TagType.Byte;
                addToNode(node, newNode);
            }
        });
        shortMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagListNode newNode = new TagListNode(new TagList());
                newNode.type = TagType.Short;
                addToNode(node, newNode);
            }
        });
        intMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagListNode newNode = new TagListNode(new TagList());
                newNode.type = TagType.Int;
                addToNode(node, newNode);
            }
        });
        longMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagListNode newNode = new TagListNode(new TagList());
                newNode.type = TagType.Long;
                addToNode(node, newNode);
            }
        });
        floatMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagListNode newNode = new TagListNode(new TagList());
                newNode.type = TagType.Float;
                addToNode(node, newNode);
            }
        });
        doubleMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagListNode newNode = new TagListNode(new TagList());
                newNode.type = TagType.Double;
                addToNode(node, newNode);
            }
        });
        byteArrayMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagListNode newNode = new TagListNode(new TagList());
                newNode.type = TagType.Byte_Array;
                addToNode(node, newNode);
            }
        });
        byteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagListNode newNode = new TagListNode(new TagList());
                newNode.type = TagType.Byte;
                addToNode(node, newNode);
            }
        });
        intArrayMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagListNode newNode = new TagListNode(new TagList());
                newNode.type = TagType.Int_Array;
                addToNode(node, newNode);
            }
        });
        stringMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagListNode newNode = new TagListNode(new TagList());
                newNode.type = TagType.String;
                addToNode(node, newNode);
            }
        });
        compoundMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagListNode newNode = new TagListNode(new TagList());
                newNode.type = TagType.Compound;
                addToNode(node, newNode);
            }
        });
        listMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TagBaseNode node = getSelectedNode();
                TagListNode newNode = new TagListNode(new TagList());
                newNode.type = TagType.List;
                addToNode(node, newNode);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void addToNode(TagBaseNode parent, TagBaseNode newNode) {
        if (parent instanceof TagCompoundNode) {
            TagCompoundNode node = (TagCompoundNode) parent;
            int i = 1;
            while (true) {
                if (node.getNodeByKey("property" + i) == null) {
                    break;
                }
                i++;
            }
            BiMap<String, TagBaseNode> key2Node = (BiMap<String, TagBaseNode>) node.getData();
            key2Node.put("property" + i, newNode);
            node.add(newNode);
        } else if (parent instanceof TagListNode) {
            TagListNode list = (TagListNode) parent;
            java.util.List<TagBaseNode> data = (List<TagBaseNode>) list.getData();
            data.add(newNode);
            parent.add(newNode);
        }
        parent.updateUserObject();
        newNode.updateUserObject();
        tagTree.expandPath(new TreePath(parent));
        tagTree.updateUI();
    }

    public static void main(String[] args) {
        System.out.println("Arguments: " + Arrays.toString(args));
        File argFile = null;
        if (args.length > 0) {
            argFile = new File(args[0]);
            if (!argFile.exists() || !argFile.isFile()) {
                argFile = null;
            }
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }


        final JFrame editorFrame = new JFrame("Keyle - NBT-Editor");
        Gui gui = new Gui(editorFrame, argFile);
        editorFrame.setContentPane(gui.mainPanel);
        editorFrame.pack();
        editorFrame.setVisible(true);
        editorFrame.setLocationRelativeTo(null);
        editorFrame.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {
            }

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            public void windowClosed(WindowEvent e) {
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowActivated(WindowEvent e) {
            }

            public void windowDeactivated(WindowEvent e) {
            }
        });
    }

    private void createUIComponents() {



        /*
        String nbtFile = new FileDialog(skilltreeCreatorFrame).getFile();
        TagCompound root;
        try {
            root = TagStream.readTag(new FileInputStream(nbtFile), true);
        } catch (IOException e) {
            return;
        }
        */


        TagCompound root;
        if (openedFile != null && openedFile.exists()) {
            System.out.println("Opening: " + openedFile.getName());

            try {
                root = TagStream.readTag(new FileInputStream(openedFile), true);
            } catch (IOException e) {
                return;
            }
        } else {
            root = new TagCompound();
        }

        tagTree = new JTree(new DefaultTreeModel(new TagCompoundNode(root)));
        tagTree.setLargeModel(true);
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer() {
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                DefaultMutableTreeNode currentTreeNode = (DefaultMutableTreeNode) value;
                if (currentTreeNode instanceof TagBaseNode) {
                    TagBaseNode node = (TagBaseNode) currentTreeNode;

                    switch (node.getTagType()) {
                        case Byte:
                            setClosedIcon(BYTE_ICON);
                            setOpenIcon(BYTE_ICON);
                            setLeafIcon(BYTE_ICON);
                            break;
                        case Byte_Array:
                            setClosedIcon(BYTE_ARRAY_ICON);
                            setOpenIcon(BYTE_ARRAY_ICON);
                            setLeafIcon(BYTE_ARRAY_ICON);
                            break;
                        case Compound:
                            setClosedIcon(COMPOUND_ICON);
                            setOpenIcon(COMPOUND_ICON);
                            setLeafIcon(COMPOUND_ICON);
                            break;
                        case Double:
                            setClosedIcon(DOUBLE_ICON);
                            setOpenIcon(DOUBLE_ICON);
                            setLeafIcon(DOUBLE_ICON);
                            break;
                        case Float:
                            setClosedIcon(FLOAT_ICON);
                            setOpenIcon(FLOAT_ICON);
                            setLeafIcon(FLOAT_ICON);
                            break;
                        case Int:
                            setClosedIcon(INT_ICON);
                            setOpenIcon(INT_ICON);
                            setLeafIcon(INT_ICON);
                            break;
                        case Int_Array:
                            setClosedIcon(INT_ARRAY_ICON);
                            setOpenIcon(INT_ARRAY_ICON);
                            setLeafIcon(INT_ARRAY_ICON);
                            break;
                        case List:
                            setClosedIcon(LIST_ICON);
                            setOpenIcon(LIST_ICON);
                            setLeafIcon(LIST_ICON);
                            break;
                        case Long:
                            setClosedIcon(LONG_ICON);
                            setOpenIcon(LONG_ICON);
                            setLeafIcon(LONG_ICON);
                            break;
                        case Short:
                            setClosedIcon(SHORT_ICON);
                            setOpenIcon(SHORT_ICON);
                            setLeafIcon(SHORT_ICON);
                            break;
                        case String:
                            setClosedIcon(STRING_ICON);
                            setOpenIcon(STRING_ICON);
                            setLeafIcon(STRING_ICON);
                            break;
                    }
                }
                return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            }
        };
        tagTree.setCellRenderer(renderer);
    }

    public TagBaseNode getSelectedNode() {
        TreePath path = tagTree.getSelectionPath();
        if (path != null) {
            return (TagBaseNode) path.getLastPathComponent();
        }
        return null;
    }

    public void openFile(File file) {
        TagCompound root = null;
        try {
            root = TagStream.readTag(new FileInputStream(file), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root == null) {
            try {
                root = TagStream.readTag(new FileInputStream(file), false);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        tagTree.setModel(new DefaultTreeModel(new TagCompoundNode(root)));
        tagTree.updateUI();
        openedFile = file;
        System.out.println("opened " + file.getName());
    }
}