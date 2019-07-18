package arca.knote.adapter

import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import arca.knote.activities.MainActivity

class RecyclerItemClickListener(activity: MainActivity, recyclerView: RecyclerView ) : GestureDetector.SimpleOnGestureListener(), RecyclerView.OnItemTouchListener {
    public interface OnItemClickListener {
        fun onItemClick(view: View, position : Int)
        fun onItemLongClick(view: View, position : Int)
    }

    private var mListener : OnItemClickListener? = activity
    private var mGestureDetector : GestureDetector  = GestureDetector(activity, this)
    private var rv: RecyclerView = recyclerView

    override fun onSingleTapUp(e: MotionEvent) : Boolean {
        return true
    }

    override fun onLongPress(e: MotionEvent) {
        val childView = rv.findChildViewUnder(e.getX(), e.getY())
        if(childView != null)
            mListener?.onItemLongClick(childView, rv.getChildAdapterPosition(childView))
    }

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent) : Boolean {
        val childView = view.findChildViewUnder(e.getX(), e.getY())
        if(childView != null && mGestureDetector.onTouchEvent(e))
            mListener?.onItemClick(childView, view.getChildAdapterPosition(childView))
        return false
    }

    override fun onTouchEvent(p0: RecyclerView, p1: MotionEvent) {
    }
    override fun onRequestDisallowInterceptTouchEvent(p0: Boolean) {
    }
}