all: paper.pdf


paper.pdf: paper.tex chapters/1_introduction.tex chapters/2_methods.tex
	pdflatex paper.tex
	bibtex paper
	pdflatex paper.tex


clean:
	rm -r *.aux *.bbl *.blg *.log *.bcf *.run.xml *-blx.bib paper.pdf


.PHONY: all clean