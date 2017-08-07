package cn.weiyf.dlframe.sample

import android.graphics.Point
import android.os.Bundle
import android.widget.ImageView
import cn.weiyf.dlframe.adapter.SingleTypeAdapter
import cn.weiyf.dlframe.base.BaseCompatActivity

class MainActivity : BaseCompatActivity() {
    val adapter = SingleTypeAdapter<Any>(1)

    override fun initViews(savedInstanceState: Bundle?) {
        adapter.setOnItemClickListener<Point, ImageView> { position, date, view ->

        }
    }

}
