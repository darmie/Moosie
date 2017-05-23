package com.akinlaja.darmie;

import android.app.Activity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.akinlaja.darmie.*;
import com.taobao.luaview.activity.LuaViewActivity;
import com.taobao.luaview.global.LuaView;
import com.taobao.luaview.global.LuaViewConfig;
import com.taobao.luaview.global.LuaViewManager;
import com.taobao.luaview.vm.extend.DebugLib;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.io.InputStream;

public class MainActivity extends Activity {

    private LuaView mLuaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LuaViewConfig.setDebug(true);

        LuaView.createAsync(this, new LuaView.CreatedCallback(){
            @Override
            public void onCreated(LuaView luaView){
                mLuaView = luaView;
                MainActivity parent = MainActivity.this;
                if(mLuaView != null){
                    //LuaValue activity = CoerceJavaToLua.coerce(parent);
                    //LuaValue contxt = CoerceJavaToLua.coerce(parent.getApplicationContext());
                    //LuaValue debug = CoerceJavaToLua.coerce();
                    setContentView(mLuaView);
                    mLuaView.setUseStandardSyntax(false);
                    mLuaView.register("AndroidActivity", MainActivity.this);
                    mLuaView.register("AndroidContext", MainActivity.this.getApplicationContext());
                    //mLuaView.register("lpeg", lpeg.class);
                    //mLuaView.register("debug", org.luaj.vm2.lib.DebugLib.class);
                    mLuaView.registerLibs(new org.luaj.vm2.lib.DebugLib());
                    mLuaView.load("Bootstrap.lua", null, null);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLuaView != null) {
            mLuaView.onDestroy();
        }
    }

    public String readFile(String path) {
        String text = "";
        //InputStream myfile = this.findResource(path);

        try {
            InputStream is = getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
            //Log.d("MOONTEST", text);
            return text;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }
}
