package com.example.mylocation.utils

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun AppCompatActivity.addFragment(@IdRes containerID: Int, fragment: Fragment?) {
    fragment?.let {
        if (this.supportFragmentManager.findFragmentByTag(it.javaClass.simpleName) == null) {
            this.supportFragmentManager.beginTransaction()
                .addToBackStack(fragment.javaClass.simpleName)
                .add(containerID, fragment, fragment.javaClass.simpleName)
                .commitAllowingStateLoss()
        }
    }
}
fun FragmentActivity.addFragment(@IdRes containerID: Int, fragment: Fragment?) {
    fragment?.let {
        if (this.supportFragmentManager.findFragmentByTag(it.javaClass.simpleName) == null) {
            this.supportFragmentManager.beginTransaction()
                .addToBackStack(fragment.javaClass.simpleName)
                .add(containerID, fragment, fragment.javaClass.simpleName)
                .commitAllowingStateLoss()
        }
    }
}