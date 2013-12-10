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

package de.keyle.knbte.tags;

import de.keyle.knbt.TagBase;
import de.keyle.knbt.TagString;

import javax.swing.*;

public class TagStringNode extends TagBaseNode {
    String data;

    public TagStringNode(TagString tag) {
        super(tag);
        data = tag.getStringData();
    }

    @Override
    public TagBase toTag() {
        return new TagString(data);
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void editValue() {
        JPanel pane = new JPanel();
        JTextArea textField = new JTextArea(data);
        JScrollPane scrollBar = new JScrollPane(textField);
        textField.setRows(10);
        textField.setColumns(35);
        textField.setBorder(BorderFactory.createBevelBorder(1));
        pane.add(scrollBar);
        int result = JOptionPane.showConfirmDialog(null, pane, "Enter a String", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            data = textField.getText();
            updateUserObject();
        }
    }

    @Override
    public void updateUserObject() {
        userObject = getUserObjectPrefix() + "\"" + data + "\"";
    }
}