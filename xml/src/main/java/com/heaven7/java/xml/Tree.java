/*
 * Copyright 2019
 * heaven7(donshine723@gmail.com)

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.heaven7.java.xml;

import java.io.IOException;

/**
 * the tree .used for read xml.
 * @author heaven7
 */
/*public*/ interface Tree {

    boolean hasNextChild();

    Tree nextChild();

    Tree getParent();

    String getBodyText() throws IOException;

    String getName() throws IOException;

    String getValue();

}
