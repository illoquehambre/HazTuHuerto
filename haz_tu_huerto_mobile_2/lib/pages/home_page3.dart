import 'package:blurrycontainer/blurrycontainer.dart';
import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:haz_tu_huerto_mobile_2/models/user.dart';
import 'package:haz_tu_huerto_mobile_2/pages/garden_list_page.dart';
import 'package:haz_tu_huerto_mobile_2/pages/home_page.dart';
import 'package:haz_tu_huerto_mobile_2/pages/new_quest_page.dart';
import 'question_list_page.dart';
class HomePage3 extends StatelessWidget {
  final User user;

  const HomePage3({super.key, required this.user});

  @override
  Widget build(BuildContext context) {
    return HomePageStates3(user: user);
  }
}

class HomePageStates3 extends StatefulWidget {
  final User user;
  const HomePageStates3({super.key, required this.user});

  @override
  State<HomePageStates3> createState() => _HomePageStates3State(user);
}

class _HomePageStates3State extends State<HomePageStates3> {
  final User user;
  _HomePageStates3State(this.user);

  void _onItemSelected(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  int _selectedIndex = 0;

  @override
  Widget build(BuildContext context) {
    List<Widget> screens = [
      
      const QuestionPage(),  
      const GardenPage(),
      const NewQuestPage(),
      HomePage(user: user)
      /*   
      EventsPage(),
      NewPostPage(),
      SearchPage(),
      ProfilePage()
      */
    ];

    return Container(
      child: Scaffold(
        backgroundColor: Colors.transparent,
        body: screens[_selectedIndex],
        extendBody: true,
        bottomNavigationBar: Container(
          decoration: const BoxDecoration(
            color: Colors.transparent,
            border: Border(
              top: BorderSide(
                color: Color.fromARGB(255, 99, 99, 99),
              ),
            ),
          ),
          child: BlurryContainer(
            color: Color.fromARGB(255, 109, 212, 116).withOpacity(0.35),
            blur: 8,
            elevation: 4,
            borderRadius: const BorderRadius.all(Radius.zero),
            padding: EdgeInsets.zero,
            child: Padding(
              padding: const EdgeInsets.fromLTRB(10, 10, 10, 25),
              child: GNav(
                gap: 8,
                onTabChange: (value) {
                  _onItemSelected(value);
                },
                tabs: const [
                  GButton(
                    icon: Icons.home,
                    iconSize: 25,
                    text: "Home",
                    iconColor: Colors.white,
                    iconActiveColor: Colors.black,
                  ),
                  GButton(
                    icon: Icons.forest_outlined,
                    iconSize: 25,
                    text: "Garden",
                    iconColor: Colors.white,
                    iconActiveColor: Colors.black,
                  ),
                  GButton(
                    icon: Icons.add_circle_outline,
                    iconSize: 25,
                    text: "Post",
                    iconColor: Colors.white,
                    iconActiveColor: Colors.black,
                  ),
                  GButton(
                    icon: Icons.search,
                    iconSize: 25,
                    text: "Search",
                    iconColor: Colors.white,
                    iconActiveColor: Colors.black,
                  ),
                  GButton(
                    icon: Icons.account_circle,
                    iconSize: 25,
                    text: "Profile",
                    iconColor: Colors.white,
                    iconActiveColor: Colors.black,
                  )
                ],
                backgroundColor: Colors.transparent,
                tabBackgroundColor: Colors.white,
                padding: const EdgeInsets.all(16),
              ),
            ),
          ),
        ),
      ),
    );
  }
}
