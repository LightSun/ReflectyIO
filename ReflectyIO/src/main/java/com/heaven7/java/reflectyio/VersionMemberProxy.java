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
