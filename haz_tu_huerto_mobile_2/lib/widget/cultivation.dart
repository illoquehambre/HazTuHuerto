import 'package:flutter/material.dart';
import 'package:haz_tu_huerto_mobile_2/models/cultivation/cultivation_dto.dart';
import 'package:haz_tu_huerto_mobile_2/rest/rest_client.dart';

class Cultivation extends StatefulWidget {
  final CultivationDto cultivation;
  final BuildContext context;
  final int num;

  const Cultivation(
      {super.key,
      required this.cultivation,
      required this.context,
      required this.num});

  @override
  State<Cultivation> createState() => _CultivationState();
}

class _CultivationState extends State<Cultivation> {
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
        child: GestureDetector(
          /*
      onTap: () {
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
              return  CultivationDetailsPage(id: widget.cultivation.id);
            },
          ),
        );
      },*/
      child: Card(
              shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10)),
              margin: EdgeInsets.all(15),
              elevation: 10,
              child: Column(
                children: <Widget>[
                  ListTile(
                    contentPadding: EdgeInsets.fromLTRB(15, 10, 25, 0),
                    title: Text(widget.cultivation.name),
                    subtitle: Text(
                        widget.cultivation.daysLeft.toString()),
                    leading: Text(widget.cultivation.numNotes.toString(), style: textTheme.bodySmall),
                  ),
                  Container(
                    constraints: BoxConstraints(
                        maxHeight: 250), // Establece la altura máxima deseada
                    child: Image.network(
                      ApiConstants.baseUrl + "/download/${widget.cultivation.cultivationImg}",
                      fit: BoxFit.cover,
                    ),
                  ),
                  
                ],
              ),
            )
    ));
  }

  _onScroll() {
    setState(() {});
  }
}
