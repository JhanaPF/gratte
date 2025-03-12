package com.example.domain.gpui

import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGaussianBlurFilter.VERTEX_SHADER

internal class GPUImageCRTFilter : GPUImageFilter(VERTEX_SHADER, CRT_FRAGMENT_SHADER) {
    companion object {
        // Custom fragment shader to simulate CRT effect.
        val CRT_FRAGMENT_SHADER = """
            precision mediump float;
            varying highp vec2 textureCoordinate;
            uniform sampler2D inputImageTexture;
            
            void main() {
                vec2 uv = textureCoordinate;
                
                // Simulate scanlines: adjust the frequency and intensity as needed.
                float scanline = sin(uv.y * 800.0) * 0.05;
                
                // Get the base color.
                vec3 color = texture2D(inputImageTexture, uv).rgb;
                
                // Apply scanline effect: slightly darken the color on each line.
                color -= scanline;
                
                // Optional: apply a simple vignette effect for extra retro vibe.
                float dist = distance(uv, vec2(0.5, 0.5));
                color *= smoothstep(0.8, 0.4, dist);
                
                gl_FragColor = vec4(color, 1.0);
            }
        
        """.trimIndent()
    }
}
