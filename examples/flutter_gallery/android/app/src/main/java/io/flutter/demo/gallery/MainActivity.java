// Copyright 2017 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.demo.gallery;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;

public class MainActivity extends FlutterActivity {
    private FlutterGalleryInstrumentation instrumentation;

    /** Instrumentation for testing. */
    public FlutterGalleryInstrumentation getInstrumentation() {
        return instrumentation;
    }
}
