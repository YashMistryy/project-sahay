package com.YashMistry.sahay.Prevalent;

import android.content.Context;
import android.content.Intent;

public class CommonIntent {
    public CommonIntent(Context context , Class<?> myclass) {
        Intent intent  = new Intent(context,myclass);
        context.startActivity(intent);

    }
}
