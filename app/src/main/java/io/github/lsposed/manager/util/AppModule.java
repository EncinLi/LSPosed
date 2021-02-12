package io.github.lsposed.manager.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.PictureDrawable;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;

import io.github.lsposed.manager.App;
import io.github.lsposed.manager.R;
import io.github.lsposed.manager.util.svg.SvgDecoder;
import io.github.lsposed.manager.util.svg.SvgDrawableTranscoder;
import me.zhanghai.android.appiconloader.glide.AppIconModelLoader;

@GlideModule
public class AppModule extends AppGlideModule {
    @Override
    public void registerComponents(Context context, @NonNull Glide glide, Registry registry) {
        int iconSize = context.getResources().getDimensionPixelSize(R.dimen.app_icon_size);
        registry.prepend(PackageInfo.class, Bitmap.class, new AppIconModelLoader.Factory(iconSize,
                context.getApplicationInfo().loadIcon(context.getPackageManager()) instanceof AdaptiveIconDrawable, context));
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(App.getOkHttpClient());
        registry.replace(GlideUrl.class, InputStream.class, factory);
        registry.register(SVG.class, PictureDrawable.class, new SvgDrawableTranscoder())
                .append(InputStream.class, SVG.class, new SvgDecoder());
    }
}
