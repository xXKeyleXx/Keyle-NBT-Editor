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
import de.keyle.knbt.TagIntArray;

public class TagIntArrayNode extends TagBaseNode {
    int[] data;

    public TagIntArrayNode(TagIntArray tag) {
        super(tag);
        data = tag.getIntArrayData().clone();
    }

    @Override
    public TagBase toTag() {
        return new TagIntArray(data);
    }

    @Override
    public void updateUserObject() {
        userObject = getUserObjectPrefix() + "[" + data.length + " Integers]";
    }

    @Override
    public void editValue() {
        //ToDo add Table
    }

    @Override
    public Object getData() {
        return data;
    }
}