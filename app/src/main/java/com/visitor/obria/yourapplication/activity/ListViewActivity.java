package com.visitor.obria.yourapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.visitor.obria.yourapplication.R;
import com.visitor.obria.yourapplication.adapter.PersonAdapter;
import com.visitor.obria.yourapplication.dao.PersonBean;
import com.visitor.obria.yourapplication.dao.PersonBeanDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListViewActivity extends AppCompatActivity {

    @BindView(R.id.lv_person)
    ListView lvPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ButterKnife.bind(this);
        init();
    }

    private PersonBeanDao mPersonBeanDao;
    PersonAdapter mPersonAdapter;

    private void init() {

        mPersonBeanDao = MyApplication.getInstance().getPersonBeanDao();
        List<PersonBean> list = mPersonBeanDao.loadAll();

        mPersonAdapter = new PersonAdapter(this, 0, list);
        lvPerson.setAdapter(mPersonAdapter);
    }

    @OnClick({R.id.iv_add, R.id.iv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
                PersonAddActivity.Start(this);
                break;
            case R.id.iv_delete:
                break;
        }
    }
}
