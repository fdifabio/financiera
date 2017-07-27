package ar.edu.unrn.lia.bean.util;

import ar.edu.unrn.lia.config.ParamValue;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by Federico on 16/06/2017.
 */
@Configuration
public class DatosPDF {

   /* @ParamValue(key = "pdf.imagen.width")
    private static Float imagenWidth;

    @ParamValue(key = "pdf.imagen.height")
    private static Float imagenHeight;

    @ParamValue(key = "pdf.aligncenter")
    private static Integer alignCenter;

    @ParamValue(key = "pdf.alignright")
    private static Integer alignRight;

    @ParamValue(key = "pdf.aligncleft")
    private static Integer alignLeft;

    @ParamValue(key = "pdf.alignmiddle")
    private static Integer alignMiddle;

    @ParamValue(key = "pdf.font.name")
    private static String fontName;

    @ParamValue(key = "pdf.font.size")
    private static Integer fontSize;

    @ParamValue(key = "pdf.font.size.leyenda")
    private static Integer fontSizeLeyenda;

    @ParamValue(key = "pdf.qr.absolute.y")
    private static Float absoluteX;

    @ParamValue(key = "pdf.qr.absolute.x")
    private static Float absoluteY;

    @ParamValue(key = "pdf.qr.percent")
    private static Float percent;

    @ParamValue(key = "pdf.font.titulo")
    private static Integer fontTitulo;

    @ParamValue(key = "pdf.font.detalle")
    private static Integer fontDetalle;

    @ParamValue(key = "pdf.font.underline")
    private static Integer fontUnderline;

    @ParamValue(key = "pdf.odt.footer.tabla1")
    private static String footerTabla1;

    @ParamValue(key = "pdf.odt.footer.tabla2")
    private static String footerTabla2;

    @ParamValue(key = "pdf.odt.encabezado.parrafo1")
    private static String encabezadoParrafo1;

    @ParamValue(key = "pdf.odt.encabezado.parrafo2")
    private static String encabezadoParrafo2;

    @ParamValue(key = "pdf.odt.encabezado.parrafo3")
    private static String encabezadoParrafo3;

    @ParamValue(key = "pdf.ods.cupon.parrafo1")
    private static String cuponParrafo1;

    @ParamValue(key = "pdf.ods.cupon.parrafo2")
    private static String cuponParrafo2;

    @ParamValue(key = "pdf.ods.cupon.parrafo3")
    private static String cuponParrafo3;

    @ParamValue(key = "pdf.ods.cupon.parrafo4")
    private static String cuponParrafo4;

    @ParamValue(key = "pdf.ods.cupon.parrafo5")
    private static String cuponParrafo5;

    @ParamValue(key = "pdf.ods.cupon.parrafo6")
    private static String cuponParrafo6;

    @ParamValue(key = "pdf.ods.depositante.parrafo1")
    private static String depositanteParrafo1;

    @ParamValue(key = "pdf.ods.depositante.parrafo2")
    private static String depositanteParrafo2;

    @ParamValue(key = "pdf.ods.depositante.parrafo3")
    private static String depositanteParrafo3;

    @ParamValue(key = "pdf.ods.depositante.parrafo4")
    private static String depositanteParrafo4;

    @ParamValue(key = "pdf.ods.qr.parrafo1")
    private static String qrParrafo1;

    @ParamValue(key = "pdf.ods.qr.parrafo2")
    private static String qrParrafo2;

    @ParamValue(key = "pdf.ley.provincial.5216")
    private static String pdfLeyProvincial;

    @ParamValue(key = "pdf.ods.cupon.parrafo.visada")
    private static String formvisadoCPA;

    public static Document crearImagen(Document document, String url) throws DocumentException, IOException {
        Image imagen = Image.getInstance(url);
        imagen.scaleAbsoluteWidth(imagenWidth);
        imagen.scaleAbsoluteHeight(imagenHeight);
        imagen.setAlignment(alignCenter);
        document.add(imagen);
        return document;
    }


    public static Document crearEncabezado(Document document, Long id, String nomenclatura) throws DocumentException {
        document.add(new Paragraph(encabezadoParrafo1, FontFactory.getFont(fontName, fontSize)));
        document.add(new Paragraph("\n"));
        Paragraph paragraph1 = new Paragraph(encabezadoParrafo2 + id, FontFactory.getFont(fontName, fontSize, fontTitulo));
        Paragraph paragraph2 = new Paragraph(encabezadoParrafo3 + nomenclatura, FontFactory.getFont(fontName, fontSize, fontTitulo));
        paragraph1.setAlignment(alignRight);
        paragraph2.setAlignment(alignRight);
        document.add(paragraph1);
        document.add(paragraph2);
        document.add(new Paragraph("\n"));
        return document;
    }

    public static PdfPCell getCellImagen(Image image) {
        PdfPCell cell = new PdfPCell(image);
        cell.setPadding(2);
        cell.setPaddingLeft(5);
        cell.setBorderWidthBottom(0);
        cell.setBorderWidthTop(0);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthLeft(0);
        return cell;
    }

    public static PdfPCell getCell(String text, int alignment, String fontname, int size, int font, String position) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(fontname, size, font)));
        cell.setPadding(2);
        cell.setPaddingLeft(5);
        cell.setHorizontalAlignment(alignment);
        if (position == "header") {
            cell.setBorderWidthTop(1f);
            cell.setBorderWidthBottom(0);
            cell.setBorderWidthRight(1f);
            cell.setBorderWidthLeft(1f);
        } else if (position == "footer") {
            cell.setBorderWidthBottom(1f);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(1f);
            cell.setBorderWidthLeft(1f);
        } else if (position == "vacio") {
            cell.setBorderWidthBottom(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthLeft(0);
        } else {
            cell.setBorderWidthBottom(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(1f);
            cell.setBorderWidthLeft(1f);
        }

        return cell;
    }

    public static Document crearDatosPerfilComitente(Document document, PerfilAGR perfilAGR, Comitente comitente) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(80);

        table.addCell(getCell("Datos del profesional:", alignLeft, fontName, fontSize, fontTitulo, "header"));
        table.addCell(getCell("Comitente:", alignLeft, fontName, fontSize, fontTitulo, "header"));

        table.addCell(getCell("\n", alignLeft, fontName, fontSize, fontDetalle, ""));
        table.addCell(getCell("\n", alignLeft, fontName, fontSize, fontDetalle, ""));

        table.addCell(getCell("Profesional: " + perfilAGR.getNombre() + " " + perfilAGR.getApellido(), alignLeft, fontName, fontSize, fontDetalle, ""));
        if (comitente.isEmpresa())
            table.addCell(getCell("Empresa: " + comitente.getNombre(), alignLeft, fontName, fontSize, fontDetalle, ""));
        else
            table.addCell(getCell("Particular: " + comitente.getNombre(), alignLeft, fontName, fontSize, fontDetalle, ""));

        table.addCell(getCell("Matrícula: " + perfilAGR.getMatricula(), alignLeft, fontName, fontSize, fontDetalle, ""));
        if (comitente.isEmpresa())
            table.addCell(getCell("CUIT: " + comitente.getCuit(), alignLeft, fontName, fontSize, fontDetalle, ""));
        else
            table.addCell(getCell("CUIL: " + comitente.getCuit(), alignLeft, fontName, fontSize, fontDetalle, ""));

        table.addCell(getCell("CUIT: " + perfilAGR.getCuit(), alignLeft, fontName, fontSize, fontDetalle, ""));
        table.addCell(getCell("Domicilio : " + comitente.getDomicilio(), alignLeft, fontName, fontSize, fontDetalle, ""));

        table.addCell(getCell("Título: " + perfilAGR.getTitulo(), alignLeft, fontName, fontSize, fontDetalle, ""));
        table.addCell(getCell("Localidad: " + comitente.getCiudad().getNombre(), alignLeft, fontName, fontSize, fontDetalle, ""));

        table.addCell(getCell("Domicilio: " + perfilAGR.getDomicilioReal(), alignLeft, fontName, fontSize, fontDetalle, ""));
        table.addCell(getCell("Departamento: " + comitente.getCiudad().getDepartamento().getNombre(), alignLeft, fontName, fontSize, fontDetalle, ""));

        table.addCell(getCell("Localidad: " + perfilAGR.getCiudadReal().getNombre(), alignLeft, fontName, fontSize, fontDetalle, ""));
        table.addCell(getCell("Provincia: " + comitente.getCiudad().getDepartamento().getProvincia().getNombre(), alignLeft, fontName, fontSize, fontDetalle, ""));

        table.addCell(getCell("\n", alignLeft, fontName, fontSize, fontDetalle, "footer"));
        table.addCell(getCell("\n", alignLeft, fontName, fontSize, fontDetalle, "footer"));

        document.add(table);
        document.add(new Paragraph("\n"));
        return document;
    }


    public static Document crearInmueble(Document document, EProfesional eProfesional) throws DocumentException {
        document.add(new Paragraph("Tarea a realizar:", FontFactory.getFont(fontName, fontSize, fontTitulo)));
        document.add(new Paragraph("Concepto: " + eProfesional.getTasaDescripcion(), FontFactory.getFont(fontName, fontSize)));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Tipo de inmueble:", FontFactory.getFont(fontName, fontSize, fontTitulo)));
        document.add(new Paragraph("Tipo: " + eProfesional.getInmueble().getTipo().getTipo(), FontFactory.getFont(fontName, fontSize)));
        document.add(new Paragraph("Nomenclatura: " + eProfesional.getNomenclatura(), FontFactory.getFont(fontName, fontSize)));
        document.add(new Paragraph("Unidades resultantes: " + eProfesional.getTasaUnidades(), FontFactory.getFont(fontName, fontSize)));
        document.add(new Paragraph("\n"));
        return document;
    }

    public static Document crearFooter(Document document) throws DocumentException {
        PdfPTable tablePlazo = new PdfPTable(1);
        tablePlazo.setWidthPercentage(100);
        tablePlazo.addCell(getCell(footerTabla1, alignMiddle, fontName, fontSize, fontTitulo, "header"));
        tablePlazo.addCell(getCell("..................................................................................................", alignMiddle, fontName, fontSize, 0, ""));
        tablePlazo.addCell(getCell("\n", alignLeft, fontName, fontSize, 0, "footer"));
        document.add(tablePlazo);
        document.add(new Paragraph("\n"));

        PdfPTable tableConformidad = new PdfPTable(1);
        tableConformidad.setWidthPercentage(100);
        tableConformidad.addCell(getCell(footerTabla2, alignMiddle, fontName, fontSize, fontTitulo, "header"));
        tableConformidad.addCell(getCell("\n", alignLeft, fontName, fontSize, fontDetalle, "footer"));
        document.add(tableConformidad);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("        .....................................                                                       ......................................."));
        document.add(new Paragraph("        Firma del profesional                                                          Firma del comitente ", FontFactory.getFont(fontName, fontSize, fontTitulo)));
        return document;
    }

    public static Document crearNuevaPagina(Document document) throws DocumentException {
        document.newPage();
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        return document;
    }

    public static Document crearCupon(Document document, EProfesional eProfesional, String url) throws DocumentException, IOException {
        Image imagen = Image.getInstance(url);
        imagen.scaleAbsoluteWidth(imagenWidth);
        imagen.scaleAbsoluteHeight(imagenHeight);
        imagen.setAlignment(alignCenter);
        document.add(imagen);
        if (!eProfesional.isPagada()) {
            Paragraph paragraph = new Paragraph(cuponParrafo1, FontFactory.getFont(fontName, fontSize, fontUnderline));
            paragraph.setAlignment(alignCenter);
            Paragraph paragraph2 = new Paragraph(cuponParrafo2, FontFactory.getFont(fontName, fontSize, fontUnderline));
            paragraph2.setAlignment(alignCenter);
            document.add(paragraph);
            document.add(paragraph2);
            Paragraph paragraph2a = new Paragraph(cuponParrafo3);
            paragraph2a.setAlignment(alignLeft);
            Paragraph paragraph2b = new Paragraph(cuponParrafo4);
            paragraph2b.setAlignment(alignLeft);
            document.add(paragraph2a);
            document.add(paragraph2b);
        }else{
            Paragraph paragraphLeyProvincial = new Paragraph(pdfLeyProvincial, FontFactory.getFont(fontName, fontSize));
            paragraphLeyProvincial.setAlignment(alignCenter);
            document.add(paragraphLeyProvincial);
            Paragraph paragraph = new Paragraph(formvisadoCPA, FontFactory.getFont(fontName, fontSize, fontUnderline));
            paragraph.setAlignment(alignCenter);
            document.add(paragraph);
        }
        Paragraph paragraph3 = new Paragraph(cuponParrafo5 + eProfesional.getId(), FontFactory.getFont(fontName, fontSize, fontTitulo));
        paragraph3.setAlignment(alignRight);
        Paragraph paragraph4 = new Paragraph(cuponParrafo6 + eProfesional.getNomenclatura(), FontFactory.getFont(fontName, fontSize, fontTitulo));
        paragraph4.setAlignment(alignRight);
        document.add(paragraph3);
        document.add(paragraph4);
        document.add(new Paragraph("\n"));
        return document;
    }

    public static Document crearDatosPerfilComitenteCupon(Document document, PerfilAGR perfilAGR, Comitente comitente) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(80);

        table.addCell(getCell("Datos del profesional:", alignLeft, fontName, fontSize, fontTitulo, "header"));
        table.addCell(getCell("Comitente:", alignLeft, fontName, fontSize, fontTitulo, "header"));

        table.addCell(getCell("\n", alignLeft, fontName, fontSize, fontDetalle, ""));
        table.addCell(getCell("\n", alignLeft, fontName, fontSize, fontDetalle, ""));

        table.addCell(getCell("Profesional: " + perfilAGR.getNombre() + " " + perfilAGR.getApellido(), alignLeft, fontName, fontSize, fontDetalle, ""));
        if (comitente.isEmpresa())
            table.addCell(getCell("Empresa: " + comitente.getNombre(), alignLeft, fontName, fontSize, fontDetalle, ""));
        else
            table.addCell(getCell("Particular: " + comitente.getNombre(), alignLeft, fontName, fontSize, fontDetalle, ""));

        table.addCell(getCell("Matrícula: " + perfilAGR.getMatricula(), alignLeft, fontName, fontSize, fontDetalle, ""));
        if (comitente.isEmpresa())
            table.addCell(getCell("CUIT: " + comitente.getCuit(), alignLeft, fontName, fontSize, fontDetalle, ""));
        else
            table.addCell(getCell("CUIL: " + comitente.getCuit(), alignLeft, fontName, fontSize, fontDetalle, ""));

        table.addCell(getCell("CUIT: " + perfilAGR.getCuit(), alignLeft, fontName, fontSize, fontDetalle, ""));
        table.addCell(getCell("\n", alignLeft, fontName, fontSize, fontDetalle, ""));

        table.addCell(getCell("\n", alignLeft, fontName, fontSize, fontDetalle, "footer"));
        table.addCell(getCell("\n", alignLeft, fontName, fontSize, fontDetalle, "footer"));

        document.add(table);
        document.add(new Paragraph("\n"));
        return document;
    }

    public static Document firmaDepositante(Document document, EProfesional eProfesional) throws DocumentException {
        document.add(new Paragraph("Tarea a realizar:", FontFactory.getFont(fontName, fontSize, fontTitulo)));
        document.add(new Paragraph("Concepto: " + eProfesional.getTasaDescripcion(), FontFactory.getFont(fontName, fontSize)));
        document.add(new Paragraph("\n"));
        if (!eProfesional.isPagada()) {
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            Paragraph paragraph1 = new Paragraph("Firma del depositante ", FontFactory.getFont(fontName, fontSize));
            paragraph1.setAlignment(alignCenter);
            document.add(paragraph1);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.addCell(getCell(depositanteParrafo1, alignLeft, fontName, fontSize, fontTitulo, "vacio"));
            table.addCell(getCell(depositanteParrafo2 + eProfesional.getCalcularMonto(), alignRight, fontName, fontSize, fontDetalle, "vacio"));
            document.add(table);

            document.add(new Paragraph(depositanteParrafo3, FontFactory.getFont(fontName, fontSizeLeyenda)));
            document.add(new Paragraph(depositanteParrafo4, FontFactory.getFont(fontName, fontSizeLeyenda)));
        }
        return document;
    }

    public static Document crearQR(Document document, EProfesional eProfesional) throws DocumentException {
        if (eProfesional.isPagada()) {
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            String leyenda = qrParrafo1 + eProfesional.getId() + "\n" + qrParrafo2;
            BarcodeQRCode qrcode = new BarcodeQRCode(leyenda.trim(), 1, 1, null);
            Image qrcodeImage = qrcode.getImage();
            qrcodeImage.setAbsolutePosition(0, 0);
            qrcodeImage.scalePercent(percent);
            qrcodeImage.setBorderWidthLeft(0);
            qrcodeImage.setBorderWidthRight(0);
            qrcodeImage.setBorderWidthBottom(0);
            qrcodeImage.setBorderWidthTop(0);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(80);
            table.setPaddingTop(50);

            table.addCell(getCellImagen(qrcodeImage));
            table.addCell(getCell("\n" + "\n" + "\n" + "\n" + leyenda, alignLeft, fontName, fontSizeLeyenda, fontDetalle, "vacio"));
            document.add(table);
        }
        return document;
    }*/
}
