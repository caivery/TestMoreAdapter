package com.example.administrator.testmoreadapter.testmoreadapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.testmoreadapter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XHC on 2015/11/13.
 */
public class TestActivity extends Activity {

    private ListView listView;
    private AdapterAdapter adatperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        listView = (ListView) findViewById(R.id.list);
        adatperAdapter = new AdapterAdapter();
        View view = this.getLayoutInflater().inflate(R.layout.headerview,null,false);
        adatperAdapter.addView(view);
        List list = new ArrayList<String>();
        for (int i = 0; i < 20; ++i) {
            list.add(" String " + i);
        }
        Adapter1 adapter1 = new Adapter1(list);


        List<Integer> listInt = new ArrayList<Integer>();
        for (int i = 0; i < 10; ++i) {
            listInt.add(R.drawable.a);
        }

        Adpater2 adapter2 = new Adpater2(listInt);
        adatperAdapter.addAdpater(adapter1);
        adatperAdapter.addAdpater(adapter2);


        List<Object> listObj = new ArrayList<Object>();
        for(int i = 0 ;i < 10 ; ++ i){
            if(i % 2 == 0){
                Person p = new Person();
                listObj.add(p);
            }
            else{
                Dog d = new Dog();
                listObj.add(d);
            }
        }

        Adpater3 adpater3 = new Adpater3(listObj);
        adatperAdapter.addAdpater(adpater3);


        listView.setAdapter(adatperAdapter);
    }

    class AdapterAdapter extends BaseAdapter {
        private List<ListAdapter> listAdatper;

        @Override
        public int getItemViewType(int position) {
            int offset = 0;
            int result = 0;
            for (ListAdapter la : listAdatper) {

                int size = la.getCount();
                if (position < size) {
                    result = offset + la.getItemViewType(position);
                    break;
                }
                position -= size;
                offset += la.getViewTypeCount();
            }
            return result;
        }

        public void addView(View view){
            List<View> listView = new ArrayList<View>(1);
            listView.add(view);
            this.addAdpater(new SackOfViewsAdapter(listView));
            this.notifyDataSetChanged();
        }

        @Override
        public int getViewTypeCount() {
            int total = 0;

            for (ListAdapter la : listAdatper) {
                total += la.getViewTypeCount();
            }
            return Math.max(total, 1);
        }

        AdapterAdapter() {
            listAdatper = new ArrayList<ListAdapter>();
        }

        void addAdpater(ListAdapter adapter) {
            listAdatper.add(adapter);
            notifyDataSetChanged();
        }
        void addAdpater(ListAdapter adapter , int position) {
            listAdatper.add(position,adapter);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            int total = 0;
            for (Adapter a : listAdatper) {
                total += a.getCount();
            }
            return total;
        }

        @Override
        public Object getItem(int position) {
            for (Adapter a : listAdatper) {
                int size = a.getCount();
                if (position < size) {
                    return a.getItem(position);
                }
                position -= size;
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            for (Adapter a : listAdatper) {
                int size = a.getCount();
                if (position < size) {
                    return a.getItemId(position);
                }
                position -= size;
            }
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            for (Adapter a : listAdatper) {
                int size = a.getCount();
                if (position < size) {
                    return a.getView(position, convertView, parent);
                }
                position -= size;
            }

            return null;
        }
    }


    class Adapter1 extends BaseAdapter {

        private List<String> listStr;
        private LayoutInflater inflater;
        private ViewHolder holder;

        Adapter1(List<String> listStr) {
            this.listStr = listStr;
            inflater = LayoutInflater.from(TestActivity.this);
        }

        @Override
        public int getCount() {
            return listStr.size();
        }

        @Override
        public Object getItem(int position) {
            return listStr.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item1, null, false);
                holder.tv = (TextView) convertView.findViewById(R.id.item1);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String str = listStr.get(position);
            holder.tv.setText(str);
            return convertView;
        }
    }

    class ViewHolder {
        TextView tv;
    }

    class Adpater2 extends BaseAdapter {

        private List<Integer> listInt;
        private LayoutInflater inflater;
        private ViewHolder2 holder2;

        Adpater2(List<Integer> listInt) {
            inflater = LayoutInflater.from(TestActivity.this);
            this.listInt = listInt;
        }

        @Override
        public int getCount() {
            return listInt.size();
        }

        @Override
        public Object getItem(int position) {
            return listInt.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item2, null, false);

                holder2 = new ViewHolder2();
                holder2.img = (ImageView) convertView.findViewById(R.id.img);
                convertView.setTag(holder2);
            } else {
                holder2 = (ViewHolder2) convertView.getTag();
            }
            int resource = listInt.get(position);
            holder2.img.setImageResource(resource);
            return convertView;
        }
    }

    class ViewHolder2 {
        ImageView img;
    }

    class Adpater3 extends BaseAdapter {

        private List<Object> listObj;
        private final int PERSON = 0;
        private final int DOG = 1;
        private Holder holder;
        private LayoutInflater inflater;
        Adpater3(List<Object> listObj) {
            this.listObj = listObj;
            inflater = LayoutInflater.from(TestActivity.this);
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            Object o = listObj.get(position);
            if (o instanceof Person) {
                return PERSON;
            } else if (o instanceof Dog) {
                return DOG;
            }
            return PERSON;
        }

        @Override
        public int getCount() {
            return listObj.size();
        }

        @Override
        public Object getItem(int position) {
            return listObj.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);

            if (convertView == null) {

                switch (type){
                    case PERSON:
                        holder = new ViewHolder3();
                        convertView = inflater.inflate(R.layout.person_layout,null,false);
                        ((ViewHolder3)holder).img = (ImageView)convertView.findViewById(R.id.img5);
                        ((ViewHolder3)holder).tv = (TextView)convertView.findViewById(R.id.tv);
                        convertView.setTag(holder);
                        break;
                    case DOG:
                        holder = new  ViewHolder4();
                        convertView = inflater.inflate(R.layout.dog_layout,null,false);
                        ((ViewHolder4)holder).img2 = (ImageView)convertView.findViewById(R.id.img2);
                        convertView.setTag(holder);
                        break;
                }
            } else {
                holder = (Holder)convertView.getTag();
            }
            switch (type){
                case PERSON:
                    ViewHolder3 holder3 = (ViewHolder3)holder;
                    holder3.img.setImageResource(R.drawable.b);
                    holder3.tv.setText(listObj.get(position).toString());
                    break;
                case DOG:
                    ViewHolder4 holder4 = (ViewHolder4)holder;
                    holder4.img2.setImageResource(R.drawable.a);
                    break;
            }
            return convertView;
        }
    }

    interface Holder {

    }

    class ViewHolder3 implements Holder {
        ImageView img;
        TextView tv;
    }

    class ViewHolder4 implements Holder {
     ImageView img2;
    }


}
