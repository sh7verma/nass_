package app.com.esenatenigeria.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.adapters.RecentActAdapter;
import app.com.esenatenigeria.model.RecentModel;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dev on 23/4/18.
 */

public class RecentActivitiesFragment extends BaseFragment {

    @SuppressLint("StaticFieldLeak")
    static RecentActivitiesFragment fragment;

    @SuppressLint("StaticFieldLeak")
    static Context mContext;

    @BindView(R.id.ed_search_bar)
    EditText edSearchBar;
    @BindView(R.id.img_search_cross)
    ImageView imgSearchCross;

    @BindView(R.id.rv_recent_activities)
    RecyclerView rvRecentActivities;
    RecentActAdapter mRecentAdapter;

    ArrayList<RecentModel> mRecentArray = new ArrayList<>();


    public static RecentActivitiesFragment newInstance(Context context, TextView textView) {
        fragment = new RecentActivitiesFragment();
        mContext = context;
        textView.setText(mContext.getResources().getString(R.string.recent_activities_amp_events));
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_recent_activities;
    }

    @Override
    protected void onCreateStuff() {
        setdata();
        rvRecentActivities.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvRecentActivities.setNestedScrollingEnabled(false);

        mRecentAdapter = new RecentActAdapter(getActivity(),mRecentArray,mHeight);
        rvRecentActivities.setAdapter(mRecentAdapter);
    }

    @OnClick(R.id.ll_search)
    void searchLayoutClick(){
        Toast.makeText(getActivity(),"Work in progress",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initListeners() {

        edSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    imgSearchCross.setImageDrawable(getResources().getDrawable(R.mipmap.ic_cancel_search));
                } else {
                    imgSearchCross.setImageDrawable(getResources().getDrawable(R.mipmap.ic_search));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    void setdata() {
        RecentModel model = new RecentModel();
        model.setTitle("#PromiseKept: Senate President Meets With Heads of MDAs to Address Non-Payment of Scholarships to Nigerian Students Abroad");
        model.setDescription("\n" +
                "On Wednesday, November 1st, the President of the Senate met with representatives of Ministries, Departments and Agencies (MDAs) responsible for the welfare of Nigerian Students on scholarships abroad now stranded due to non-payment of their living allowances and tuition fees by relevant government agencies. \n" +
                "\n" +
                "The Senate in plenary had mandated the President of the Senate to intervene and know why Nigerian students on scholarships in foreign countries are yet to be paid their scholarship funds. This is sequel to the Senate President’s October meeting with Nigerian students in Russia who complained about not being paid their scholarships and other allowances.");
        model.setImage("https://pbs.twimg.com/media/DNjgRViWAAA9QIS.jpg");
        mRecentArray.add(model);

        RecentModel model1 = new RecentModel();
        model1.setTitle("Committees of Public Petitions and Ethics Adopt a Standard Manual on Ethics for the National Assembly");
        model1.setDescription("In a bid to improve understanding of powers and limitations of public petition and ethics committees of the National Assembly as well as strengthen procedural strategies in dealing with public petitions and ethical issues, the National Institute for Legislative Studies (NILS) recently organized a retreat for members of the National Assembly Committee on Ethics, Privileges and Public Petitions in Lagos, Nigeria.\n" +
                "\n" +
                "In a keynote address read on behalf of the Senate Leader, Ahmed Lawan by Sen. Samuel Anyanwu, Chairman, Senate Committee on Ethics, Privileges and Public Relations, Senator Lawan said that linkages between citizens and elected representatives had remained one of the important features of democracy via public petitions.\n" +
                "He stressed the need for lawmakers to continue to work hard to earn the trust of the people by promoting greater citizen engagement in the legislative processes.\n" +
                "\n" +
                "“I hope that the knowledge you gain in this retreat will improve the work you do in your respective committees, promote citizen engagement and improve public trust in the legislature.\n" +
                "\n" +
                "Speaking on the retreat, Senator Anyanwu said that the Ethics, Privileges and Public Petitions Committee had received 525 petitions from individuals and corporate bodies since the inception of the 8th Assembly.\n" +
                "“We received 152 petitions in the first session and when Nigerians noticed that one can access justice through the committee, the number of petitions multiplied such that we received 284 in the second session.\n" +
                "“In this fresh 3rd session, we have received about 88 petitions. Not less than 92 individuals and corporate bodies have had justice procured for them without spending their personal earnings.\n" +
                "“This is in comparison to only six petitions laid in the 7th Assembly out of 77 petitions it received,’’ Anyanwu said.\n" +
                "\n" +
                "In her welcome address, Prof. Ladi Hamalai, the Director- General of the National Institute for Legislative Studies and host of the Retreat NILS, said that the capacity building programme would be integral to ensuring the success of the role of the committee.");
        model1.setImage("http://1e8q3q16vyc81g8l3h3md6q5f5e.wpengine.netdna-cdn.com/wp-content/uploads/2017/02/Nigeria-1-for-2-Target_Speech-Photo-473x271.jpg");
        mRecentArray.add(model1);

        RecentModel model2 = new RecentModel();
        model2.setTitle("SENATE PASSES EIGHT CONCURRENCE BILLS INTO LAW AS WUKARI VARSITY GETS LEGAL BACKING");
        model2.setDescription("The Senate on Thursday, September 29, 2016 passed into law nine bills and two others for second reading. The first legislation that got the nod of the Red Chamber was the Federal University, Wukari establishment etc. Bill, 2016 (S.B. 273). The Bill was presented by the Committee Chairman on Tertiary Institutions and TETFUND, Sen. Barau Jibrin.\n" +
                "The Senate desolved into the Committee of  Whole where clauses 1 – 26, short and long titles, first, second and third schedules of the Bill as well as the explanatory memorandum of understanding of the legislation were passed as recommended. The Bill was read the third time and passed.\n" +
                "Similarly, eight concurrence bills emanating from the House of Representatives also scaled through second reading and were equally considered in the Committee of the Whole and ultimately read the third time and passed into law. The Concurrence Bills standing in the name of the Senate Leader include:\n" +
                "1)\tTelecommunication and Postal Offences Act CAP T5 LFN 2004 (Amendment) Bill, 2016 (H.B. 216).\n" +
                "2)\tNational Crop Varieties, Livestock Breeds (Registration) Act CAP N27 LFN 2004 (Amendment) Bill, 2016 (H.B. 218).\n" +
                "3)\tProduce (Enforcement of Export Standards) Act CAP P32 LFN 2004 (Amendment) Bill 2016 (H.B. 219).\n" +
                "4)\tPrevention of Crimes Act CAP P27 LFN 2004 (Amendment) Bill, by increasing offences penalties, 2016 (H.B. 233).\n" +
                "5)\tWater Resources Act  \tCAP W2 LFN 2004 (Amendment) Bill to review upward stipulated fines, 2016 (H.B. 234).\n" +
                "6)\tNational Agricultural Land Development Authority Act CAP N4 LFN 2004 (Amendment) Bill 2016 (H.B. 237).\n" +
                "7)\tBee (Import Control & Management) Act CAP B6 LFN 2004 (Amendment) Bill 2016 (H.B. 238)\n" +
                "8)\tAgricultural and Rural Management Training Act CAP A10 LFN 2004 (Amendment) Bill to allow other Federal Institutions participate in the Board, 2016 (H.B. 241).\n" +
                "Meanwhile, the Senate has debated and passed for second reading two bills that include:\n" +
                "1)\tA Bill for an Act to Incorporate and Enforce certain provisions of the United Nations Convention on the Elimination of all forms of Discrimination Against Women, the Protocol to African Charter on Human and People’s Rights on the rights of Women in Africa and other matters connected therewith, 2016, (S.B 301) sponsored by Sen. Biodun Olujimi (Ekiti South) and 20 others. The Bill having scaled through second has been referred to the Senate Committee on Judiciary to report back in four weeks. \n" +
                "2)\tA Bill for an Act to Repeal the Nigerian Ports Authority Act CAP 126 LFN, 2004 and to establish the Nigerian Ports and Harbours Authority to provide for the Ownership, Management and Development of Ports and Harbours and for related matters 2016, (S.B. 234), sponsored by Sen. Andy Uba (Anambra South). The legislation has sailed through second reading and has been referred to the Senate Committee on Marine Transport to report back in two weeks.\n" +
                "Also, the Senate has received a Presidential Communication via two letters addressed to the Senate President from President Muhammadu Buhari seeking the screening and confirmation of six persons to fill vacant positions in the Independent National Electoral Commission (INEC) and five persons to fill vacant positions in National Population Commission (NPC).\n" +
                "Before adjourning sitting to Tuesday, October 4, 2016, the Senate President, Bukola Saraki wished Nigerians a happy 56th Independence Anniversary. ");
        model2.setImage("http://saharareporters.com/sites/default/files/styles/normal_medium/public/page_images/news/2014/Nigerian-Senate.jpg?itok=5tUsEo8t");
        mRecentArray.add(model2);

        RecentModel model3 = new RecentModel();
        model3.setTitle("SENATE CONSIDERS REPORT OF AD-HOC COMMITTEE ON THE STATE OF THE ECONOMY & PASSES FIVE MOTIONS");
        model3.setDescription("The Senate on Tuesday, September 27, 2016 commenced consideration of the Report of its Ad-hoc Committee on the State of the Economy. The Report presented before the Chamber for consideration has seventeen recommendations. The recommendations were extensively deliberated upon by  Senators and at the end most of them were adopted by the Chamber.\n" +
                "According to the report, the Senate of the Federal Republic of Nigeria conducted a two-day intense and wide ranging debate on the state of the Nigerian economy. It observed that the negative GDP growth of 0.36% and 2.6% in the first and second quarters of 2016 technically plunged the national economy into recession. This contraction was largely due to the fall in oil revenues and further exacerbated by the vandalism of the nation’s oil assets in the Niger Delta region as well as the country’s plummeting foreign exchange reserve from more than $60 billion in 2007 to $24 billion currently.\n" +
                "Major highlights of the report is the decision of the Upper Legislative Chamber, urging President Muhammadu Buhari not to sell the country’s national assets, and the suggestion to raise a team of experts that would engage the youth of the Niger Delta for amicable resolution of the crisis in the region. The report also summoned the Chief Financial Reporting Officer of the federation to brief the Chamber.\n" +
                "Similarly, five Motions were also debated and passed by the Senate, they include:\n" +
                "1)\tInconclusive Elections, sponsored by the Deputy Senate President, Ike Ekweremadu (Enugu West) and the Senate Leader, Muhammed Ali Ndume (Borno South). The Motion sailed through with three prayers below:\n" +
                "A)\tCall on the President of the Federal Republic of Nigeria to immediately nominate suitably qualified persons to fill the vacant positions at INEC in line with the constitution to enhance the capacity of the Institution to conduct conclusive elections.\n" +
                "B)\tCall on INEC to immediately conclude all pending re-run elections in the country.\n" +
                "C)\tConstitute an Ad-hoc Committee to hold a public hearing to review the performance of the INEC  in the last one year with a view to ascertain the factors that may have caused the perceived decline of the electoral system.\n" +
                "2)\tUnscrupulous violation of Foreign Exchange (Monitoring and Miscellaneous) Act, sponsored by Sen. Dino Melaye (Kogi West). The Motion noted with serious concern the repatriation of $13.92 billion illegally out of Nigeria by the Mobile Telecommunication Limited (MTN) through its bankers between 2006 and 2016. The Motion scaled through with single resolution below:\n" +
                "A)\tMandate the Committee on Banking, Insurance and other Financial Institutions to carry out a holistic investigation into the matter and report back to the Senate.\n" +
                "3)\tEarth Tremor and the preparedness of Nigeria to deal with the intending consequences, sponsored by Sen. Danjuma La’ah (Kaduna South). The law-maker noted that the Earth tremor that occurred in Kwoi and its environs in Jaba Local Government Area of Kaduna State on Sunday, through Monday September 11 and 12, 2016 as well as the increasing Earth tremors occurrence in Nigeria in recent time, there is the likelihood of witnessing Earth Quake in the near future. The Motion was passed with three prayers below:\n" +
                "A)\tUrge the government at all levels to take the Earth Tremor warnings in parts of the country serious and begin to take proactive measures to educate the populace on what to expect and how to react when it occurs.\n" +
                "B)\tUrge the National Emergency Management Agency (NEMA) and the Security Agencies to move in immediately through public enlightenment and drills to prepare the people against any eventuality as the resources required at this stage would be minimal.\n" +
                "C)\tMandate the Committee on Environment and Solid Minerals to undertake tour of affected communities and report back to the Senate.\n" +
                "4)\tThe need to ascertain the Degree of Local Content in Nigerian Oil and Gas industry, sponsored by Sen. Gershom Bassey (Cross River South). He stated that the Senate is aware that the National Committee on Local Content Development (NCLCD) discovered that the local participation in the upstream sector of the oil and gas industry in Nigeria was less than 5% meaning that 95% of the yearly expenditure of about $8 billion left the country. The Motion also sailed through with two resolutions below:\n" +
                "A)\tMandate the Senate Committee on Petroleum Upstream and Gas to conduct a public hearing to investigate the implementation of local content with a view to determine the degree of compliance with the Nigerian Oil and Gas Industry Content Development Act (NOGIC) by the industry operators.\n" +
                "B)\tMandate the Senate Committee on Petroleum Upstream and Gas to investigate the utilization of the Nigeria Content Development Fund.\n" +
                "5)\tLooming crisis in the Nigerian Aviation Industry, sponsored by Sen. Samuel Anyanwu (Imo East). The Law-maker noted with concern that the looming crisis in the Aviation sector portends grave danger to air safety and operations. He added that the sector has been gasping for survival over some years now but worsen by the current economic recession. The Motion was passed with two prayers below:\n" +
                "A)\tMandate the Committee on Aviation and the Ministry of Aviation to liaise with the Nigerian Civil Aviation Authority (NCAA) to urgently look into the problems faced by the airlines in order to fashion out possible solutions to the problem and avert latent danger inherent in the crisis.\n" +
                "B)\tMandate the Federal Ministry of Transport to urgently commence the rehabilitation of major highways and the rail system to serve as a viable and safe alternative to air travels.   ");
        model3.setImage("http://wrc-scci.com/image/?img=gallery_images/large-ENGLISH-1344.jpg");
        mRecentArray.add(model3);

        RecentModel model4 = new RecentModel();
        model4.setTitle("DEBATE ON THE STATE OF THE ECONOMY CONTINUES");
        model4.setDescription("The Senate on Thursday, September 22, 2016 continued the debate on the State of the Economy, having deferred it to another legislative date yesterday.  Almost half of the 109 distinguished Senators contributed to the debate during the plenary that lasted more than six hours, presided over by the Senate President, Bukola Saraki.\n" +
                "Major highlights of the deliberations were the contribution of the Senate Chief Whip, Sen. Shola Adeyeye who cited that the abuse of contract mobilization fee has resulted in the abandoning of many capital projects across the country thus littering most of the states of the federation and encouraged corruption in government.\n" +
                "Sen. Adeyeye averred that the National Assembly must be a better Parliament by giving itself a better structure that would offer panacea to the challenges of the nation. He stressed that the structure will henceforth put a stop to stealing and corruption as well as improve relationship between the federal, states and local government areas.\n" +
                "The ranking Principal Officer of the Senate urged law-makers to take a look at the Constitution Review Process and tinker with the Exclusive Legislative List with a view to reducing its burden. He opined that State Governors in Nigeria should not be blamed for lack of meaningful development in the States because the Presidency was busy spending the national money. He advised that the Presidency must  consult the Governors before dipping its hands in the country’s commonwealth.  \n" +
                "The Law-maker called on Nigerians to protect the value of the naira for the nation’s economy to be buoyant. He asserted the Central Bank of Nigeria (CBN) Governor has a parallel government by spending the country’s money without recourse to legislative approval. This must be stopped and the CBN Governor called to order. The Parliamentarian stated that for Nigeria to be saved, Nigerians must imbibe the culture of honesty and creativity in ending poverty.\n" +
                "Other Legislators that contributed to the debate include Senators Barau Jibrin, Kabiru Gaya, Bukar Abba Ibrahim, Abdullahi Adamu, Aliyu Wamakko, Omo-Agege, Andy Uba, Emmanuel Paulker, Monsurat Sunmonu, Sunny Ogbouji, Bala Ibn Na’Allah, Ben Murray Bruce, Abdullahi Abubakar Gumel, Aliyu Wakil, Danjuma La’ah, Umar Kurfi, Theodore Orji, Ibrahim Gobir, Solomon Adokwe, Abubakar Kyari, Gershom Bassey, Dada Joseph Gbolahan, Yusuf Abubakar Yusuf, Gyunka Philip Aruwa, Abdul Aziz Murtala Nyako, Jeremiah Useni, Joshua Dariye, Philip Aduda, Olamilekan Adeola Solomon, Jonah Jang, Yahaya Abdullahi, Gbenga Ashafa, Hope Uzodinma, Clifford Ordia, Ahmed Lawan, Chukwuka Utazi, Isa Hammah Misau, Faseyi Duro Samuel, Buhari Abdulfatai, Shittu Muhammed and the Senate President, Bukola Saraki among others.\n" +
                "Sen. Saraki commended  Senators for their patriotism, patience and statesmanship, having waited patiently for two days to contribute. He disclosed that the two-day debate has offered a lot of recommendations on the nation’s economic recession and challenges which he said would be streamlined on Tuesday, September 27, 2016 by an Ad-hoc Committee to be set up by the Chamber at the end of the debate.\n" +
                "The Ad-hoc Committee has been named and members include Sen. Barau Jibrin, Sen. Ben Murray Bruce, Sen. Muhammed Hassan, Sen. John Enoh and Sen. Tijani Yahaya Abdullahi as Chairman.\n" +
                "Meanwhile, Sen. Joshua Dariye has decamped from the People’s Democratic Party (PDP) to the All Progressive Congress (APC). The law-maker made his position known through a letter addressed to the Senate President which was read on the floor of the Chamber; stating that his decision was informed by the demand of his constituents to move to the ruling party, APC.\n");
        model4.setImage("http://saharareporters.com/sites/default/files/styles/normal_medium/public/page_images/news/2014/Nigerian-Senate.jpg?itok=5tUsEo8t");
        mRecentArray.add(model4);
    }

    @OnClick(R.id.img_search_cross)
    void searchClear() {
        edSearchBar.setText("");
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
