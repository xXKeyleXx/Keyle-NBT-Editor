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

import de.keyle.knbt.*;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

public class TagListNode extends TagBaseNode {
    List<TagBaseNode> data = new ArrayList<TagBaseNode>();
    public TagType type;

    public TagListNode(TagList tagList) {
        super(tagList);
        type = tagList.getType();
        this.allowsChildren = true;

        for (TagBase tag : tagList.getReadOnlyList()) {
            switch (tag.getTagType()) {
                case Byte:
                    data.add(new TagByteNode((TagByte) tag));
                    break;
                case Byte_Array:
                    data.add(new TagByteArrayNode((TagByteArray) tag));
                    break;
                case Compound:
                    data.add(new TagCompoundNode((TagCompound) tag));
                    break;
                case Double:
                    data.add(new TagDoubleNode((TagDouble) tag));
                    break;
                case Float:
                    data.add(new TagFloatNode((TagFloat) tag));
                    break;
                case Int:
                    data.add(new TagIntNode((TagInt) tag));
                    break;
                case Int_Array:
                    data.add(new TagIntArrayNode((TagIntArray) tag));
                    break;
                case List:
                    data.add(new TagListNode((TagList) tag));
                    break;
                case Long:
                    data.add(new TagLongNode((TagLong) tag));
                    break;
                case Short:
                    data.add(new TagShortNode((TagShort) tag));
                    break;
                case String:
                    data.add(new TagStringNode((TagString) tag));
                    break;
                case End:
                    continue;
            }
            this.add(data.get(data.size() - 1));
            data.get(data.size() - 1).getUserObject();
        }
        updateUserObject();
    }

    public void updateUserObject() {
        if (data.size() == 1) {
            userObject = getUserObjectPrefix() + "1 entry of type " + getTagType().name();
        }
        userObject = getUserObjectPrefix() + data.size() + " entries of type " + type.name();
    }

    @Override
    public void editValue() {
    }

    public TagType getListTagType() {
        return type;
    }

    @Override
    public TagBase toTag() {
        TagList list = new TagList();
        for (TagBaseNode node : data) {
            list.addTag(node.toTag());
        }
        return list;
    }

    @Override
    public Object getData() {
        return data;
    }

    public void remove(MutableTreeNode aChild) {
        super.remove(aChild);
        if (aChild instanceof TagBaseNode) {
            data.remove(aChild);
        }
    }

    public void remove(int childIndex) {
        super.remove(childIndex);
        TreeNode aChild = getChildAt(childIndex);
        if (aChild instanceof TagBaseNode) {
            data.remove(aChild);
        }
    }
}