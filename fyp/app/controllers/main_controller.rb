require 'open-uri'



class MainController < ApplicationController
  def first
    Dir.chdir "C:\\Users\\nach\\Documents\\NetBeansProjects"
      page=Nokogiri::HTML(open("http://www.amazon.in"))
      create_config
    if params[:query]


      params[:query].gsub!(' ','&')
      page=Nokogiri::HTML(open("http://www.flipkart.com/search?q=" + params[:query].to_s ))
      @links=page.css("a[data-tracking-id='prd_title']")


      page=Nokogiri::HTML(open("http://www.amazon.in/s/url=&field-keywords=" + params[:query].to_s ))
      @links1=page.css("a[class='a-link-normal s-access-detail-page  a-text-normal']")

      
      aFile=File.open("extra.txt","a")
      lin= @links1.length
      while  lin>0 do
        aFile.puts(@links1[lin-1].text)
        lin=lin-1
      end


      aFile.close



      @status=Status.new(:user => current_user.id)
      @status.save
    end

  end


  def create_config
    
    aFile=File.open("config.txt","w")
    aFile.puts(current_user.id.to_s + ".txt," +current_user.id.to_s + "_extra.txt")
    aFile.close
  end

  def crawl
    @links=Link.where(:search_id => current_user.id ) 
    if params[:add]
      @links=Link.new(:search_id => current_user.id, :link => params[:add])
      @links.save
      redirect_to main_crawl_path
    end

    if params[:name]
      aFile = File.open( current_user.id.to_s + "_extra.txt", "a")
      aFile.syswrite(params[:name] + ";")
    end
  end



  def delete
    Link.find(params[:id]).destroy
    redirect_to main_crawl_path
  end


  def intermediate
    @links=Link.all

    params[:urls].each do |url|
      @links=Link.new(:search_id => current_user.id, :link => url)
      @links.save
    end
    redirect_to main_crawl_path
  end

  def intermediate_2
        @links=Link.where(:search_id => current_user.id )

    @links.each do |url|

      page=Nokogiri::HTML(open( url.link ))

          if (url.link).include?("amazon")
            link=page.css("a[id='acrCustomerReviewLink']")            
            next_amazon("http://www.amazon.in" << link[0]["href"])
          else (url.link).include?("flipkart")
            link=page.css("a")
            i=link.length
            while i>0
              if (link[i-1].text).include?("Show ALL")
                next_flipkart("http://www.flipkart.com" << link[i-1]["href"].to_s)
              end
              i=i-1
            end   
          end

    end
    @status=Status.find_by(user: current_user)
    @status.crawl=1
    @status.file_write=0
    @status.save
    redirect_to backend_front_path

  end



  def reviews
  @reviews=Review.all

  end


  def next_flipkart(a)
    page=Nokogiri::HTML(open(a))
     page1=page.css("span[class='review-text']")
     @reviews=Review.all
     lin= page1.length
     while  lin>0 do
      @reviews=Review.new(:name =>current_user.id, :description => (page1[lin-1].text))
      @reviews.save
      lin=lin-1
    end
    temp=page.css("a[class='nav_bar_next_prev']")
    i=temp.length
     while i>0
      if (temp[i-1].text).include?("Next")
        next_flipkart("http://www.flipkart.com" << temp[i-1]["href"])
      end
      i=i-1
     end
  end

def next_amazon(a)
    page=Nokogiri::HTML(open(a))
     page1=page.css("div[class='a-row review-data']")
     @reviews=Review.all
     lin= page1.length
     while  lin>0 do
      @reviews=Review.new(:name =>current_user.id, :description => (page1[lin-1].text))
      @reviews.save
      lin=lin-1
      end
    temp=page.css("a")
    i=temp.length
     while i>0
      if (temp[i-1].text).include?("Next")
        next_amazon("http://www.amazon.in" << temp[i-1]["href"])
      end
      i=i-1
     end
  end



def next_generic(a)

  page=Nokogiri::HTML(open(a))
     page1=page.css("div[class='a-row review-data']")
     @reviews=Review.all
     lin= page1.length
     while  lin>0 do
      @reviews=Review.new(:name =>current_user.id, :description => (page1[lin-1].text))
      @reviews.save
      lin=lin-1
      end
    temp=page.css("a")
    i=temp.length
     while i>0
      if (temp[i-1].text).include?("Next")
        next_amazon("http://www.amazon.in" << temp[i-1]["href"])
      end
      i=i-1
     end
  end



  def test
      aFile = File.open("temp.txt", "a")
      if aFile
       aFile.syswrite("ABCDEF")
      else
       puts "Unable to open file!"
      end
      


      @op= %x(cd .. &  ls )

     
  end



end
