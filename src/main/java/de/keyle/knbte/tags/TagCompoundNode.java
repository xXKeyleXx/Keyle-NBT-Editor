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

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.keyle.knbt.*;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class TagCompoundNode extends TagBaseNode {
    BiMap<String, TagBaseNode> key2Node = HashBiMap.create();
    BiMap<TagBaseNode, String> node2Key = key2Node.inverse();

    public TagCompoundNode(TagCompound tagCompound) {
        super(tagCompound);
        this.allowsChildren = true;

        for (String key : tagCompound.getCompoundData().keySet()) {
            TagBase tag = tagCompound.get(key);

            switch (tag.getTagType()) {
                case Byte:
                    key2Node.put(key, new TagByteNode((TagByte) tag));
                    break;
                case Byte_Array:
                    key2Node.put(key, new TagByteArrayNode((TagByteArray) tag));
                    break;
                case Compound:
                    key2Node.put(key, new TagCompoundNode((TagCompound) tag));
                    break;
                case Double:
                    key2Node.put(key, new TagDoubleNode((TagDouble) tag));
                    break;
                case Float:
                    key2Node.put(key, new TagFloatNode((TagFloat) tag));
                    break;
                case Int:
                    key2Node.put(key, new TagIntNode((TagInt) tag));
                    break;
                case Int_Array:
                    key2Node.put(key, new TagIntArrayNode((TagIntArray) tag));
                    break;
                case List:
                    key2Node.put(key, new TagListNode((TagList) tag));
                    break;
                case Long:
                    key2Node.put(key, new TagLongNode((TagLong) tag));
                    break;
                case Short:
                    key2Node.put(key, new TagShortNode((TagShort) tag));
                    break;
                case String:
                    key2Node.put(key, new TagStringNode((TagString) tag));
                    break;
                case End:
                    continue;
            }
            this.add(key2Node.get(key));
            key2Node.get(key).getUserObject();
        }
        updateUserObject();
        sortNodes();
    }

    public TagBaseNode getNodeByKey(String key) {
        return key2Node.get(key);
    }

    public String getKeyByNode(TagBaseNode node) {
        return node2Key.get(node);
    }

    @Override
    public Object getData() {
        return key2Node;
    }

    @Override
    public TagBase toTag() {
        TagCompound compound = new TagCompound();
        for (String key : key2Node.keySet()) {
            compound.getCompoundData().put(key, key2Node.get(key).toTag());
        }
        return compound;
    }

    public void updateUserObject() {
        if (key2Node.size() == 1) {
            userObject = getUserObjectPrefix() + "1 entry";
        }
        userObject = getUserObjectPrefix() + key2Node.size() + " entries";
    }

    @Override
    public void editValue() {
    }

    public void remove(MutableTreeNode aChild) {
        super.remove(aChild);
        if (aChild instanceof TagBaseNode) {
            node2Key.remove(aChild);
        }
    }

    public void remove(int childIndex) {
        TreeNode aChild = getChildAt(childIndex);
        super.remove(childIndex);
        if (aChild instanceof TagBaseNode) {
            node2Key.remove(aChild);
        }
    }
}