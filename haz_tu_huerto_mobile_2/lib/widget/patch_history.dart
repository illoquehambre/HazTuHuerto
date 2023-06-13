import 'package:flutter/material.dart';
import 'package:haz_tu_huerto_mobile_2/models/patch/patch_history.dart';
import 'package:haz_tu_huerto_mobile_2/widget/bottom_loader.dart';
import 'package:haz_tu_huerto_mobile_2/widget/cultivation.dart';
import 'package:haz_tu_huerto_mobile_2/widget/patch.dart';

class PatchHistory extends StatefulWidget {
  final PatchHistoryDto patch;
  final BuildContext context;

  const PatchHistory({super.key, required this.patch, required this.context});

  @override
  State<PatchHistory> createState() => _PatchHistoryState();
}

class _PatchHistoryState extends State<PatchHistory> {
  final _scrollController = ScrollController();

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _scrollController.addListener(_onScroll);
  }

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    return Material(
      child: Center(
          child: Column(
            children: <Widget>[
              Text(
                widget.patch.id.toString(),
                style: textTheme.bodySmall,
              ),
              Text(widget.patch.name, style: textTheme.bodySmall),
        
              SizedBox(
              height: 350, // Establece una altura específica para el ListView
              child: ListView.builder(
                itemBuilder: (BuildContext context, int index) {
                  return index >= widget.patch.cultivationHistory.length
                      ? const BottomLoader()
                      : Cultivation(num:1, cultivation: widget.patch.cultivationHistory[index],
                      context: context,);
                },
                itemCount: widget.patch.cultivationHistory.length),
                ),
              const SizedBox(
                height: 12,
              ),
              /*
              ElevatedButton(
                child: const Text('Update'),
                onPressed: () {
                  Navigator.push(
                    context,
                    PageRouteBuilder(
                      transitionDuration: const Duration(milliseconds: 500),
                      transitionsBuilder:
                          (context, animation, secondaryAnimation, child) {
                        return FadeTransition(
                          opacity: animation,
                          child: child,
                        );
                      },
                      pageBuilder: (context, animation, secondaryAnimation) {
                        return UpdatePatchPage(id: widget.patch.id);
                      },
                    ),
                  );
                },
              ),*/
              /*
              ElevatedButton(
                  onPressed: () async {
                    print("Check");
                    JwtAuthenticationService service =
                        getIt<JwtAuthenticationService>();
                    await service.getCurrentUser();
                  },
                  child: const Text('Check'))
                  */
            ],
          ),
      ),
    );
  }

  _onScroll() {
    setState(() {});
  }
}
