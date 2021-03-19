package com.vbeeon.iotdbs.presentation.fragment

import android.os.Bundle
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_switch_detail_change_color.*
import org.json.JSONObject


@Suppress("DEPRECATION")
class DemoFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel
    companion object {
        fun newInstance(id: String): DemoFragment {
            val fragment = DemoFragment()
            val args = Bundle()
            args.putString("switch_id", id)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_timer
    }

    override fun initView() {
        val json : String = "{\n" +
                "    \"responsePrimitive\": [\n" +
                "        {\n" +
                "            \"responseStatusCode\": 2000,\n" +
                "            \"requestIdentifier\": \"1234bvluikjn5\",\n" +
                "            \"primitiveContent\": {\n" +
                "                \"anyOrAny\": [\n" +
                "                    {\n" +
                "                        \"m2m:cin\": {\n" +
                "                            \"cs\": 17,\n" +
                "                            \"st\": 1,\n" +
                "                            \"ct\": \"20210318T235317\",\n" +
                "                            \"con\": {\n" +
                "                                \"sw_report\": 1\n" +
                "                            },\n" +
                "                            \"ty\": 4,\n" +
                "                            \"ri\": \"/sii01/cin-b66c51280a0fe65a8\",\n" +
                "                            \"lt\": \"20210318T235317\",\n" +
                "                            \"pi\": \"/sii01/cnt-d70fff8c3257e1fb\",\n" +
                "                            \"cnf\": \"application/json\",\n" +
                "                            \"rn\": \"cin-0aef415505d511eab\",\n" +
                "                            \"cr\": \"a798e50518a1f88b16c46de26b60104370a3a3c5f273426feddf80c4306cf41e\",\n" +
                "                            \"et\": \"20310318T235317\"\n" +
                "                        }\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            \"originatingTimestamp\": \"1616140043547\",\n" +
                "            \"content\": {\n" +
                "                \"m2m:cin\": {\n" +
                "                    \"cs\": 17,\n" +
                "                    \"st\": 1,\n" +
                "                    \"ct\": \"20210318T235317\",\n" +
                "                    \"con\": {\n" +
                "                        \"sw_report\": 1\n" +
                "                    },\n" +
                "                    \"ty\": 4,\n" +
                "                    \"ri\": \"/sii01/cin-b66c51280a0fe65a8\",\n" +
                "                    \"lt\": \"20210318T235317\",\n" +
                "                    \"pi\": \"/sii01/cnt-d70fff8c3257e1fb\",\n" +
                "                    \"cnf\": \"application/json\",\n" +
                "                    \"rn\": \"cin-0aef415505d511eab\",\n" +
                "                    \"cr\": \"a798e50518a1f88b16c46de26b60104370a3a3c5f273426feddf80c4306cf41e\",\n" +
                "                    \"et\": \"20310318T235317\"\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"responseStatusCode\": 2000,\n" +
                "            \"requestIdentifier\": \"1234bvluikjn5\",\n" +
                "            \"primitiveContent\": {\n" +
                "                \"anyOrAny\": [\n" +
                "                    {\n" +
                "                        \"m2m:cin\": {\n" +
                "                            \"cs\": 17,\n" +
                "                            \"st\": 1,\n" +
                "                            \"ct\": \"20210205T084409\",\n" +
                "                            \"con\": {\n" +
                "                                \"sw_report\": 1\n" +
                "                            },\n" +
                "                            \"ty\": 4,\n" +
                "                            \"ri\": \"/sii01/cin-ae28e0ac041a4101dc\",\n" +
                "                            \"lt\": \"20210205T084409\",\n" +
                "                            \"pi\": \"/sii01/cnt-092069edf42c3b09e\",\n" +
                "                            \"cnf\": \"application/json\",\n" +
                "                            \"rn\": \"cin-775adb1719adb0ec\",\n" +
                "                            \"cr\": \"a798e50518a1f88b16c46de26b60104370a3a3c5f273426feddf80c4306cf41e\",\n" +
                "                            \"et\": \"20310205T084409\"\n" +
                "                        }\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            \"originatingTimestamp\": \"1616140043492\",\n" +
                "            \"content\": {\n" +
                "                \"m2m:cin\": {\n" +
                "                    \"cs\": 17,\n" +
                "                    \"st\": 1,\n" +
                "                    \"ct\": \"20210205T084409\",\n" +
                "                    \"con\": {\n" +
                "                        \"sw_report\": 1\n" +
                "                    },\n" +
                "                    \"ty\": 4,\n" +
                "                    \"ri\": \"/sii01/cin-ae28e0ac041a4101dc\",\n" +
                "                    \"lt\": \"20210205T084409\",\n" +
                "                    \"pi\": \"/sii01/cnt-092069edf42c3b09e\",\n" +
                "                    \"cnf\": \"application/json\",\n" +
                "                    \"rn\": \"cin-775adb1719adb0ec\",\n" +
                "                    \"cr\": \"a798e50518a1f88b16c46de26b60104370a3a3c5f273426feddf80c4306cf41e\",\n" +
                "                    \"et\": \"20310205T084409\"\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"responseStatusCode\": 2000,\n" +
                "            \"requestIdentifier\": \"1234bvluikjn5\",\n" +
                "            \"primitiveContent\": {\n" +
                "                \"anyOrAny\": [\n" +
                "                    {\n" +
                "                        \"m2m:cin\": {\n" +
                "                            \"cs\": 17,\n" +
                "                            \"st\": 1,\n" +
                "                            \"ct\": \"20210319T055900\",\n" +
                "                            \"con\": {\n" +
                "                                \"sw_report\": 1\n" +
                "                            },\n" +
                "                            \"ty\": 4,\n" +
                "                            \"ri\": \"/sii01/cin-cbff8093e5aa9e55c\",\n" +
                "                            \"lt\": \"20210319T055900\",\n" +
                "                            \"pi\": \"/sii01/cnt-0cfdf2472c7456284\",\n" +
                "                            \"cnf\": \"application/json\",\n" +
                "                            \"rn\": \"cin-5803970e9819a7ca8\",\n" +
                "                            \"cr\": \"a798e50518a1f88b16c46de26b60104370a3a3c5f273426feddf80c4306cf41e\",\n" +
                "                            \"et\": \"20310319T055900\"\n" +
                "                        }\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            \"originatingTimestamp\": \"1616140043505\",\n" +
                "            \"content\": {\n" +
                "                \"m2m:cin\": {\n" +
                "                    \"cs\": 17,\n" +
                "                    \"st\": 1,\n" +
                "                    \"ct\": \"20210319T055900\",\n" +
                "                    \"con\": {\n" +
                "                        \"sw_report\": 1\n" +
                "                    },\n" +
                "                    \"ty\": 4,\n" +
                "                    \"ri\": \"/sii01/cin-cbff8093e5aa9e55c\",\n" +
                "                    \"lt\": \"20210319T055900\",\n" +
                "                    \"pi\": \"/sii01/cnt-0cfdf2472c7456284\",\n" +
                "                    \"cnf\": \"application/json\",\n" +
                "                    \"rn\": \"cin-5803970e9819a7ca8\",\n" +
                "                    \"cr\": \"a798e50518a1f88b16c46de26b60104370a3a3c5f273426feddf80c4306cf41e\",\n" +
                "                    \"et\": \"20310319T055900\"\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"responseStatusCode\": 2000,\n" +
                "            \"requestIdentifier\": \"1234bvluikjn5\",\n" +
                "            \"primitiveContent\": {\n" +
                "                \"anyOrAny\": [\n" +
                "                    {\n" +
                "                        \"m2m:cin\": {\n" +
                "                            \"cs\": 17,\n" +
                "                            \"st\": 1,\n" +
                "                            \"ct\": \"20210319T040707\",\n" +
                "                            \"con\": {\n" +
                "                                \"sw_report\": 1\n" +
                "                            },\n" +
                "                            \"ty\": 4,\n" +
                "                            \"ri\": \"/sii01/cin-e50effe542a0310d6\",\n" +
                "                            \"lt\": \"20210319T040707\",\n" +
                "                            \"pi\": \"/sii01/cnt-987ed7af04470ce008\",\n" +
                "                            \"cnf\": \"application/json\",\n" +
                "                            \"rn\": \"cin-400555d32bf90c64a\",\n" +
                "                            \"cr\": \"a798e50518a1f88b16c46de26b60104370a3a3c5f273426feddf80c4306cf41e\",\n" +
                "                            \"et\": \"20310319T040707\"\n" +
                "                        }\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            \"originatingTimestamp\": \"1616140043494\",\n" +
                "            \"content\": {\n" +
                "                \"m2m:cin\": {\n" +
                "                    \"cs\": 17,\n" +
                "                    \"st\": 1,\n" +
                "                    \"ct\": \"20210319T040707\",\n" +
                "                    \"con\": {\n" +
                "                        \"sw_report\": 1\n" +
                "                    },\n" +
                "                    \"ty\": 4,\n" +
                "                    \"ri\": \"/sii01/cin-e50effe542a0310d6\",\n" +
                "                    \"lt\": \"20210319T040707\",\n" +
                "                    \"pi\": \"/sii01/cnt-987ed7af04470ce008\",\n" +
                "                    \"cnf\": \"application/json\",\n" +
                "                    \"rn\": \"cin-400555d32bf90c64a\",\n" +
                "                    \"cr\": \"a798e50518a1f88b16c46de26b60104370a3a3c5f273426feddf80c4306cf41e\",\n" +
                "                    \"et\": \"20310319T040707\"\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"responseStatusCode\": 2000,\n" +
                "            \"requestIdentifier\": \"1234bvluikjn5\",\n" +
                "            \"primitiveContent\": {\n" +
                "                \"anyOrAny\": [\n" +
                "                    {\n" +
                "                        \"m2m:cin\": {\n" +
                "                            \"cs\": 17,\n" +
                "                            \"st\": 1,\n" +
                "                            \"ct\": \"20210319T071351\",\n" +
                "                            \"con\": {\n" +
                "                                \"sw_report\": 1\n" +
                "                            },\n" +
                "                            \"ty\": 4,\n" +
                "                            \"ri\": \"/sii01/cin-014b599f6e0b1fea7\",\n" +
                "                            \"lt\": \"20210319T071351\",\n" +
                "                            \"pi\": \"/sii01/cnt-d5260f099250048a3f8\",\n" +
                "                            \"cnf\": \"application/json\",\n" +
                "                            \"rn\": \"cin-54081b2a4d5afcb1\",\n" +
                "                            \"cr\": \"a798e50518a1f88b16c46de26b60104370a3a3c5f273426feddf80c4306cf41e\",\n" +
                "                            \"et\": \"20310319T071351\"\n" +
                "                        }\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            \"originatingTimestamp\": \"1616140043503\",\n" +
                "            \"content\": {\n" +
                "                \"m2m:cin\": {\n" +
                "                    \"cs\": 17,\n" +
                "                    \"st\": 1,\n" +
                "                    \"ct\": \"20210319T071351\",\n" +
                "                    \"con\": {\n" +
                "                        \"sw_report\": 1\n" +
                "                    },\n" +
                "                    \"ty\": 4,\n" +
                "                    \"ri\": \"/sii01/cin-014b599f6e0b1fea7\",\n" +
                "                    \"lt\": \"20210319T071351\",\n" +
                "                    \"pi\": \"/sii01/cnt-d5260f099250048a3f8\",\n" +
                "                    \"cnf\": \"application/json\",\n" +
                "                    \"rn\": \"cin-54081b2a4d5afcb1\",\n" +
                "                    \"cr\": \"a798e50518a1f88b16c46de26b60104370a3a3c5f273426feddf80c4306cf41e\",\n" +
                "                    \"et\": \"20310319T071351\"\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"responseStatusCode\": 4004,\n" +
                "            \"requestIdentifier\": \"1234bvluikjn5\",\n" +
                "            \"primitiveContent\": {\n" +
                "                \"anyOrAny\": [\n" +
                "                    \"Could not found parent resource\"\n" +
                "                ]\n" +
                "            },\n" +
                "            \"originatingTimestamp\": \"1616140043356\",\n" +
                "            \"content\": \"Could not found parent resource\",\n" +
                "            \"contentType\": \"text/plain\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"responseStatusCode\": 4004,\n" +
                "            \"requestIdentifier\": \"1234bvluikjn5\",\n" +
                "            \"primitiveContent\": {\n" +
                "                \"anyOrAny\": [\n" +
                "                    \"Could not found parent resource\"\n" +
                "                ]\n" +
                "            },\n" +
                "            \"originatingTimestamp\": \"1616140043363\",\n" +
                "            \"content\": \"Could not found parent resource\",\n" +
                "            \"contentType\": \"text/plain\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"responseStatusCode\": 2000,\n" +
                "            \"requestIdentifier\": \"1234bvluikjn5\",\n" +
                "            \"primitiveContent\": {\n" +
                "                \"anyOrAny\": [\n" +
                "                    {\n" +
                "                        \"m2m:cin\": {\n" +
                "                            \"cs\": 17,\n" +
                "                            \"st\": 1,\n" +
                "                            \"ct\": \"20210319T073811\",\n" +
                "                            \"con\": {\n" +
                "                                \"sw_report\": 1\n" +
                "                            },\n" +
                "                            \"ty\": 4,\n" +
                "                            \"ri\": \"/sii01/cin-7d030b17da50fb7e5\",\n" +
                "                            \"lt\": \"20210319T073811\",\n" +
                "                            \"pi\": \"/sii01/cnt-0f6d2b26aa1193a2\",\n" +
                "                            \"cnf\": \"application/json\",\n" +
                "                            \"rn\": \"cin-7fafe3ed4be65236\",\n" +
                "                            \"cr\": \"a798e50518a1f88b16c46de26b60104370a3a3c5f273426feddf80c4306cf41e\",\n" +
                "                            \"et\": \"20310319T073811\"\n" +
                "                        }\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            \"originatingTimestamp\": \"1616140043651\",\n" +
                "            \"content\": {\n" +
                "                \"m2m:cin\": {\n" +
                "                    \"cs\": 17,\n" +
                "                    \"st\": 1,\n" +
                "                    \"ct\": \"20210319T073811\",\n" +
                "                    \"con\": {\n" +
                "                        \"sw_report\": 1\n" +
                "                    },\n" +
                "                    \"ty\": 4,\n" +
                "                    \"ri\": \"/sii01/cin-7d030b17da50fb7e5\",\n" +
                "                    \"lt\": \"20210319T073811\",\n" +
                "                    \"pi\": \"/sii01/cnt-0f6d2b26aa1193a2\",\n" +
                "                    \"cnf\": \"application/json\",\n" +
                "                    \"rn\": \"cin-7fafe3ed4be65236\",\n" +
                "                    \"cr\": \"a798e50518a1f88b16c46de26b60104370a3a3c5f273426feddf80c4306cf41e\",\n" +
                "                    \"et\": \"20310319T073811\"\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"responseStatusCode\": 2000,\n" +
                "            \"requestIdentifier\": \"1234bvluikjn5\",\n" +
                "            \"primitiveContent\": {\n" +
                "                \"anyOrAny\": [\n" +
                "                    {\n" +
                "                        \"m2m:cin\": {\n" +
                "                            \"cs\": 17,\n" +
                "                            \"st\": 1,\n" +
                "                            \"ct\": \"20210319T073811\",\n" +
                "                            \"con\": {\n" +
                "                                \"sw_report\": 1\n" +
                "                            },\n" +
                "                            \"ty\": 4,\n" +
                "                            \"ri\": \"/sii01/cin-bafe05f6aee3ea1f3\",\n" +
                "                            \"lt\": \"20210319T073811\",\n" +
                "                            \"pi\": \"/sii01/cnt-81f5cb88e435678b\",\n" +
                "                            \"cnf\": \"application/json\",\n" +
                "                            \"rn\": \"cin-2bbd8d7ba6b18bc9\",\n" +
                "                            \"cr\": \"a798e50518a1f88b16c46de26b60104370a3a3c5f273426feddf80c4306cf41e\",\n" +
                "                            \"et\": \"20310319T073811\"\n" +
                "                        }\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            \"originatingTimestamp\": \"1616140043653\",\n" +
                "            \"content\": {\n" +
                "                \"m2m:cin\": {\n" +
                "                    \"cs\": 17,\n" +
                "                    \"st\": 1,\n" +
                "                    \"ct\": \"20210319T073811\",\n" +
                "                    \"con\": {\n" +
                "                        \"sw_report\": 1\n" +
                "                    },\n" +
                "                    \"ty\": 4,\n" +
                "                    \"ri\": \"/sii01/cin-bafe05f6aee3ea1f3\",\n" +
                "                    \"lt\": \"20210319T073811\",\n" +
                "                    \"pi\": \"/sii01/cnt-81f5cb88e435678b\",\n" +
                "                    \"cnf\": \"application/json\",\n" +
                "                    \"rn\": \"cin-2bbd8d7ba6b18bc9\",\n" +
                "                    \"cr\": \"a798e50518a1f88b16c46de26b60104370a3a3c5f273426feddf80c4306cf41e\",\n" +
                "                    \"et\": \"20310319T073811\"\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}"
        val jsnobject = JSONObject(json)
        val jsonArray = jsnobject.getJSONArray("responsePrimitive")
        for (i in 0 until jsonArray.length()) {
            val explrObject = jsonArray.getJSONObject(i)
        }
    }

    override fun initViewModel() {

    }

    override fun observable() {

    }


}