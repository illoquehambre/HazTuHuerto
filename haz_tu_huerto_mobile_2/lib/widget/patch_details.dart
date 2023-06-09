import 'package:flutter/material.dart';
import 'package:haz_tu_huerto_mobile_2/models/patch/patch_details_dto.dart';
import 'package:haz_tu_huerto_mobile_2/pages/update_garden_page.dart';
import 'package:haz_tu_huerto_mobile_2/widget/bottom_loader.dart';
import 'package:haz_tu_huerto_mobile_2/widget/note.dart';

class PatchDetails extends StatefulWidget {
  final PatchDetailsDto patch;
  final BuildContext context;

  const PatchDetails({super.key, required this.patch, required this.context});

  @override
  State<PatchDetails> createState() => _PatchDetailsState();
}

class _PatchDetailsState extends State<PatchDetails> {
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
              widget.patch.id as String,
              style: textTheme.bodySmall,
            ),
            Text(widget.patch.name, style: textTheme.bodySmall),
            ListView.builder(
              itemBuilder: (BuildContext context, int index) {
                return index >= widget.patch.cultivation.notes.length
                    ? const BottomLoader()
                    : Note(num:1, note: widget.patch.cultivation.notes[index],
                    context: context,);
              },
              itemCount: widget.patch.cultivation.notes.length),
            const SizedBox(
              height: 12,
            ),
           /* ElevatedButton(
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
                      return UpdateGardenPage(id: widget.patch.id);
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
