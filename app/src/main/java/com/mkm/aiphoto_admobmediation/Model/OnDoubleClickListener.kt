package com.mkm.aiphoto_admobmediation.Model

interface OnDoubleClickListener {
    fun onPhotoViewDoubleClick(view: PhotoView, entity: MultiTouchEntity)
    fun onBackgroundDoubleClick()
}
