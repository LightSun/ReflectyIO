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
package com.heaven7.java.reflectyio;

import com.heaven7.java.reflecty.MemberProxy;

/**
 * the member proxy which focus on the version.
 * @author heaven7
 */
public interface VersionMemberProxy extends MemberProxy {

    /**
     * called this to judge the target version is matched or not.
     * if not match means this member shouldn't not be serialize or deserialize
     * @param expectVersion the expect version
     * @return true if version matched.
     */
    boolean isVersionMatched(float expectVersion);

}
