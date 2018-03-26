package com.buzz.models;

import com.buzz.utils.GlobalConst;

/**
 * Created by NickChung on 3/18/15.
 */
public class LogonUser {
    public String userId;
    //public String userNameCN;
    //public String userNameEN;
    //public String userNameTW;
    //public String userNamePT;
    public String nickName;
    public String email;
    public String defaultLang;
    public String voiceLang;
    public boolean isLogon;
    public String autoPlay;


    public LogonUser() {
        this.defaultLang = GlobalConst.DEFAULT_LANG_CN;
        this.voiceLang = GlobalConst.VOICE_LANG_SC;
        this.autoPlay = GlobalConst.AUTO_PLAY_ON;
    }
}
