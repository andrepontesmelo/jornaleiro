module Jornaleiro
  # Requires linux bash and pdftotext external tool
  class BashTools
    attr_accessor :path

    def initialize(path)
      @path = path
    end

    def pdf_to_text_pages(pdf)
      output = `pdfinfo #{pdf} | grep Pages:`
      page_count = output.split(' ').last.to_i

      [1..page_count].each do |p|
        pdf_page_to_text pdf, p
      end

      puts "mv #{@path}/#{f} #{DOU_BACKUP_PATH}/#{f} "
      system("mv #{@path}/#{f} #{DOU_BACKUP_PATH}/#{f} ")
    end

    def pdf_page_to_text(pdf, page)
      print '.'
      pdf_page = "#{pdf}_#{page}"
      cleaned_pdf = "#{pdf_page}_cleaned"

      system("pdftotext -raw -f #{page} -l #{page} #{pdf} #{pdf_page} ")
      system("tr '\n' ' ' < #{pdf_page} | tr -s ' ' >> #{cleaned_pdf}")
      system("sed -i -- 's/- //g' '#{cleaned_pdf}'")
      system("mv #{cleaned_pdf} #{pdf_page}")
    end

    def remove_dot_sequences
      puts "Removing common '. ' sequences "
      system('for file in *.transcribed \
        ; do sed -i -- "s/. . / /g" "$file"; done')
    end

    def shrink_spaces
      puts 'Shrinking empty spaces and removing line breaks'
      system('for file in *.transcribed; do tr "\n" " " < "$file" \
       | tr -s " " >> "$file".cleaned; done')
    end

    def remove_hyphenation
      puts 'Removing hyphenation'
      system('for file in *.cleaned; do sed -i -- "s/- //g" "$file"; done')
    end

    def pdf_to_text_pages_pages
      Dir.chdir(folder) do
        files = Dir.entries('.')

        files.each do |f|
          pdf = f[/\w+.pdf/]
          pdf_to_text_pages pdf unless pdf.nil?
        end
      end
    end

    def pdf_to_text
      Dir.chdir(@path) do
        puts 'Transcribing pdf to text '
        system('for file in *.pdf*; do pdftotext \
          -raw "$file" "$file.transcribed"; done')
      end
    end

    def download_files_parallel(links_file)
      Dir.chdir(@path) do
        puts 'Downloading pdfs.'

        command = "cat #{links_file} | parallel \
         --no-notice  --gnu \"wget -q {}\""

        puts system(command)
      end
    end

    def download_file(url)
      Dir.chdir(@path) do
        puts system("wget -q \"#{url}\"")
      end
    end

    def crawle_pdfs(file)
      Dir.chdir(@path) do
        puts 'downloading pdfs ..'
        command_line = "cat #{file} | parallel  --gnu --no-notice 'wget -q {}'"
        puts command_line
        system(command_line)

        pdf_to_text
        remove_dot_sequences
        shrink_spaces
        remove_hyphenation
      end
    end
  end
end
