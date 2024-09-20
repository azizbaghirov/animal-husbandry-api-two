package az.eagro.animalhusbandry.business.certification;

import az.eagro.animalhusbandry.business.BusinessException;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.certification.BreedingAnimalFarmCertificateEntity;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PdfGenerator {

    private static final String IMAGE_KTN = "/images/ktn.jpg";
    private static final String CLASSPATH_FONTS_FIRA_SANS_REGULAR_TTF = "classpath:fonts/FiraSans-Regular.ttf";
    private static final float IMAGE_SCALE_WIDTH = 90f;
    private static final float IMAGE_SCALE_HEIGHT = 82f;
    private static final float OFFSET_IMAGE_X = 220f;
    private static final float OFFSET_IMAGE_Y = -50f;

    public byte[] generate(BreedingAnimalFarmCertificateEntity certificate, CertificationApplicationEntity application, String farmer,
            String operator) throws IOException {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);

            document.open();

            document.addTitle("Registry excerpt");
            document.addCreator(operator);

            Paragraph paragraph = addImage(document);

            File fontFile = ResourceUtils.getFile(CLASSPATH_FONTS_FIRA_SANS_REGULAR_TTF);

            BaseFont baseFont = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            Font fontSixteenBold = new Font(baseFont, 16, Font.BOLD, BaseColor.BLACK);
            String firstHeaderText = "AZƏRBAYCAN RESPUBLİKASININ\n KƏND TƏSƏRRÜFATI NAZİRLİYİ";
            createSingleParagraph(document, paragraph, fontSixteenBold, firstHeaderText, 65f, Element.ALIGN_CENTER);

            Font fontThirteenNormal = new Font(baseFont, 13, Font.NORMAL, BaseColor.BLACK);
            String secondHeaderText = "DAMAZLIQ HEYVANDARLIQ SUBYEKTLƏRİNİN DÖVLƏT REYESTRİNDƏN \n ÇIXARIŞ";
            createSingleParagraph(document, paragraph, fontThirteenNormal, secondHeaderText, 15f, Element.ALIGN_CENTER);

            Font fontTenBoldKey = new Font(baseFont, 10, Font.BOLD, BaseColor.BLACK);
            Font fontTenNormalValue = new Font(baseFont, 10, Font.NORMAL, BaseColor.BLACK);
            createTable(document, fontTenBoldKey, fontTenNormalValue, paragraph, certificate);

            String registeredSubject = "Qeydiyyata alınan subyektin adı: ";
            addKeyValueToDocument(document, registeredSubject, farmer, fontTenBoldKey, fontTenNormalValue, 15f);

            String currentAddress = "Faktiki ünvan: ";
            addKeyValueToDocument(document, currentAddress, application.getFarm().getCurrentAddress(), fontTenBoldKey, fontTenNormalValue, 10f);

            String activityType = "Fəaliyyət istiqaməti: ";
            addKeyValueToDocument(document, activityType, certificate.getActivityType().getLabel(), fontTenBoldKey, fontTenNormalValue, 10f);

            Font fontElevenBoldKey = new Font(baseFont, 11, Font.BOLD, BaseColor.BLACK);
            Font fontElevenNormalValue = new Font(baseFont, 11, Font.NORMAL, BaseColor.BLACK);
            String specification = "Damazlıq heyvandarlıq subyektinin fəaliyyət istiqaməti:  ";
            addKeyValueToDocument(document, specification, certificate.getFarmSpecialization().getName(),
                    fontElevenBoldKey, fontElevenNormalValue, 10f);

            String farmType = "Damazlıq heyvandarlıq subyektinin fəaliyyət növü:  ";
            addKeyValueToDocument(document, farmType, certificate.getFarmType().getName(), fontElevenBoldKey, fontElevenNormalValue, 40f);

            String text = "Bu çıxarışla təsdiq edilir ki, damazlıq heyvandarlıq sahəsində fəaliyyət göstərən subyekt"
                    + " damazlıq təsərrüfat kimi qeydiyyatına alınmışdır.";
            paragraph.setIndentationLeft(20f);
            createSingleParagraph(document, paragraph, fontElevenNormalValue, text, 70f, Element.ALIGN_LEFT);

            String bottomText = "Azərbaycan Respublikasının\nKənd Təsərrüfatı Nazirliyinin\nSəlahiyyətli nümayəndəsi: ";
            addKeyValueToDocument(document, bottomText, operator, fontElevenBoldKey, fontElevenNormalValue, 40f);

            paragraph.setIndentationRight(40f);
            createSingleParagraph(document, paragraph, fontThirteenNormal, certificate.getRegistrationNumber(), 50f, Element.ALIGN_RIGHT);

            document.close();

            return outputStream.toByteArray();
        } catch (DocumentException e) {
            throw new BusinessException(e.getMessage());
        }

    }

    private Paragraph addImage(Document document) throws IOException, DocumentException {
        Paragraph paragraph = new Paragraph();
        Image image = Image.getInstance(getClass().getResource(IMAGE_KTN));
        image.scaleToFit(IMAGE_SCALE_WIDTH, IMAGE_SCALE_HEIGHT);
        paragraph.add(new Chunk(image, OFFSET_IMAGE_X, OFFSET_IMAGE_Y));
        document.add(paragraph);
        paragraph.clear();
        return paragraph;
    }

    private void createTable(
            Document document, Font fontTenBoldKey, Font fontNineNormalValue, Paragraph paragraph, BreedingAnimalFarmCertificateEntity certificate)
            throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setSpacingBefore(50f);
        table.setWidthPercentage(93f);

        String[] keys = {"Qeydiyyat nömrəsi: ", "", "Qeydiyyat tarixi: "};
        String[] values = {certificate.getRegistrationNumber(), "", certificate.getCertificationDate().toString()};
        Phrase phrase;
        for (int i = 0; i < keys.length; i++) {
            PdfPCell cell = new PdfPCell();
            cell.setBorder(0);

            phrase = new Phrase();

            paragraph.setFont(fontTenBoldKey);
            paragraph.add(keys[i]);
            phrase.add(paragraph);
            paragraph.clear();

            paragraph.setFont(fontNineNormalValue);
            paragraph.add(values[i]);
            phrase.add(paragraph);
            paragraph.clear();

            cell.addElement(new Paragraph(phrase));
            table.addCell(cell);
        }
        document.add(table);
    }

    private void createSingleParagraph(Document document, Paragraph paragraph, Font fontRegistry, String text, float spacing, int element)
            throws DocumentException {
        paragraph.setFont(fontRegistry);
        paragraph.add(text);

        paragraph.setAlignment(element);
        paragraph.setSpacingBefore(spacing);
        document.add(paragraph);
        paragraph.clear();
    }

    private void addKeyValueToDocument(Document document, String key, String value, Font fontKey, Font fontValue, float spacingBefore)
            throws DocumentException {
        Paragraph paragraph = new Paragraph();
        Phrase phrase = new Phrase();

        paragraph.setFont(fontKey);
        paragraph.add(key);
        phrase.add(paragraph);
        paragraph.clear();

        paragraph.setFont(fontValue);
        paragraph.add(value);
        phrase.add(paragraph);

        paragraph = new Paragraph(phrase);

        paragraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.setSpacingBefore(spacingBefore);
        paragraph.setIndentationLeft(20f);

        document.add(paragraph);
    }

}

