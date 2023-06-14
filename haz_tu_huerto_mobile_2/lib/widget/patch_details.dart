import 'package:flutter/material.dart';
import 'package:haz_tu_huerto_mobile_2/config/locator.dart';
import 'package:haz_tu_huerto_mobile_2/models/patch/patch_details_dto.dart';
import 'package:haz_tu_huerto_mobile_2/pages/new_note_page.dart';
import 'package:haz_tu_huerto_mobile_2/pages/patch_history_page.dart';
import 'package:haz_tu_huerto_mobile_2/pages/update_garden_page.dart';
import 'package:haz_tu_huerto_mobile_2/rest/rest_client.dart';
import 'package:haz_tu_huerto_mobile_2/services/patch_service.dart';
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
    final _patchService = getIt<PatchService>();
    return Material(
      child: Center(
        child: Column(
          children: <Widget>[
            Card(
              shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10)),
              margin: EdgeInsets.all(15),
              elevation: 10,
              child: Column(
                children: <Widget>[
                  ListTile(
                    contentPadding: EdgeInsets.fromLTRB(15, 10, 25, 0),
                    title: Text(widget.patch.name),
                    subtitle: Text(
                        '${widget.patch.cultivation.name}\n${widget.patch.cultivation.variety}\n${widget.patch.cultivation.plantDate}\t${widget.patch.cultivation.daysPlanted}\n${widget.patch.cultivation.harvestDate}\t${widget.patch.cultivation.daysLeft}'),
                    leading: Text(widget.patch.id.toString(),
                        style: textTheme.bodySmall),
                  ),
                  Container(
                    constraints: BoxConstraints(
                        maxHeight: 220), // Establece la altura máxima deseada
                    child: Image.network(
                      ApiConstants.baseUrl +
                          "/download/${widget.patch.cultivation.cultivationImg}",
                      fit: BoxFit.cover,
                    ),
                  ),
                ],
              ),
            ),
            SizedBox(
              height: 325, // Establece una altura específica para el ListView
              child: ListView.builder(
                  itemBuilder: (BuildContext context, int index) {
                    return index >= widget.patch.cultivation.notes.length
                        ? const BottomLoader()
                        : Note(
                            num: 1,
                            note: widget.patch.cultivation.notes[index],
                            context: context,
                          );
                  },
                  itemCount: widget.patch.cultivation.notes.length),
            ),
            const SizedBox(
              height: 12,
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                ElevatedButton(
                  child: const Text('New Note'),
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
                          return NewNotePage(id: widget.patch.cultivation.id);
                        },
                      ),
                    );
                  },
                ),
                Spacer(),
                ElevatedButton(
                  child: const Text('Watch Historic'),
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
                          return PatchHistoryPage(id: widget.patch.id);
                        },
                      ),
                    );
                  },
                ),
                Spacer(),
                ElevatedButton(
                  child: const Text('Divide Patch'),
                  onPressed: () {
                    _patchService.divide(widget.patch.id);
                  },
                ),
              ],
            ),

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
