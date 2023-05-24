import 'package:blurrycontainer/blurrycontainer.dart';
import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:haz_tu_huerto_mobile_2/pages/home_page.dart';
import '../models/models.dart';
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
    List<Widget> _screens = [
      
      QuestionPage(),  
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
        body: _screens[_selectedIndex],
        extendBody: true,
        bottomNavigationBar: Container(
          decoration: BoxDecoration(
            color: Colors.transparent,
            border: Border(
              top: BorderSide(
                color: Color.fromARGB(255, 99, 99, 99),
              ),
            ),
          ),
          child: BlurryContainer(
            color: Color.fromRGBO(173, 29, 254, 1).withOpacity(0.35),
            blur: 8,
            elevation: 4,
            borderRadius: BorderRadius.all(Radius.zero),
            padding: EdgeInsets.zero,
            child: Padding(
              padding: const EdgeInsets.fromLTRB(10, 10, 10, 25),
              child: GNav(
                gap: 8,
                onTabChange: (value) {
                  _onItemSelected(value);
                },
                tabs: [
                  GButton(
                    icon: Icons.home,
                    iconSize: 25,
                    text: "Inicio",
                    iconColor: Colors.white,
                    iconActiveColor: Colors.black,
                  ),
                  GButton(
                    icon: Icons.my_library_music,
                    iconSize: 25,
                    text: "Eventos",
                    iconColor: Colors.white,
                    iconActiveColor: Colors.black,
                  ),
                  GButton(
                    icon: Icons.add_circle_outline,
                    iconSize: 25,
                    text: "Postear",
                    iconColor: Colors.white,
                    iconActiveColor: Colors.black,
                  ),
                  GButton(
                    icon: Icons.search,
                    iconSize: 25,
                    text: "Buscar",
                    iconColor: Colors.white,
                    iconActiveColor: Colors.black,
                  ),
                  GButton(
                    icon: Icons.account_circle,
                    iconSize: 25,
                    text: "Perfil",
                    iconColor: Colors.white,
                    iconActiveColor: Colors.black,
                  )
                ],
                backgroundColor: Colors.transparent,
                tabBackgroundColor: Colors.white,
                padding: EdgeInsets.all(16),
              ),
            ),
          ),
        ),
      ),
    );
  }
}
