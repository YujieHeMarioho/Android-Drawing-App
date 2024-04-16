#include <jni.h>
#include <android/bitmap.h>
#include <cstdlib>
#include <ctime>

extern "C" {

JNIEXPORT void JNICALL
Java_com_example_finaldraw_CustomView_addNoise(JNIEnv *env, jobject thiz, jobject bitmap) {
    AndroidBitmapInfo info;
    void* pixels;
    int ret;

    if ((ret = AndroidBitmap_getInfo(env, bitmap, &info)) < 0) {
        return;
    }

    if (info.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
        return;
    }

    if ((ret = AndroidBitmap_lockPixels(env, bitmap, &pixels)) < 0) {
        return;
    }

    auto *line = static_cast<uint32_t *>(pixels);
    int height = info.height;
    int width = info.width;
    srand(time(nullptr));

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            // add noise
            uint32_t pixel = line[x];
            uint32_t alpha = (pixel & 0xFF000000);
            int red = (pixel & 0x00FF0000) >> 16;
            int green = (pixel & 0x0000FF00) >> 8;
            int blue = (pixel & 0x000000FF);

            // modify each channel
            int noise = (rand() % 50) - 25;  // Noise range -25 to 24
            red = fmin(fmax(red + noise, 0), 255);
            green = fmin(fmax(green + noise, 0), 255);
            blue = fmin(fmax(blue + noise, 0), 255);

            line[x] = alpha | (red << 16) | (green << 8) | blue;
        }
        line = line + width;
    }

    AndroidBitmap_unlockPixels(env, bitmap);
}

JNIEXPORT void JNICALL
Java_com_example_finaldraw_CustomView_invertColors(JNIEnv *env, jobject thiz, jobject bitmap) {
    AndroidBitmapInfo info;
    void* pixels;
    int ret;

    if ((ret = AndroidBitmap_getInfo(env, bitmap, &info)) < 0) {
        return;
    }

    if (info.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
        return;
    }

    if ((ret = AndroidBitmap_lockPixels(env, bitmap, &pixels)) < 0) {
        return;
    }

    auto *line = static_cast<uint32_t *>(pixels);
    int height = info.height;
    int width = info.width;

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            uint32_t pixel = line[x];
            uint32_t alpha = (pixel & 0xFF000000);
            int red = 255 - ((pixel & 0x00FF0000) >> 16);
            int green = 255 - ((pixel & 0x0000FF00) >> 8);
            int blue = 255 - (pixel & 0x000000FF);

            line[x] = alpha | (red << 16) | (green << 8) | blue;
        }
        line = line + width;
    }

    AndroidBitmap_unlockPixels(env, bitmap);
}

}