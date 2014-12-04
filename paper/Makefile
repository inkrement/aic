all: paper.pdf


paper.pdf: biblio.bib paper.tex chapters/1_introduction.tex chapters/2_data.tex chapters/3_preprocessing.tex
	pdflatex paper.tex
	bibtex paper
	pdflatex paper.tex


clean:
	rm -r *.aux *.bbl *.blg *.log *.bcf *.run.xml *-blx.bib paper.pdf


.PHONY: all clean
