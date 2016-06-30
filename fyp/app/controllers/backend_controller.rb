class BackendController < ApplicationController
  

  def front
  	@status=Status.find_by(:id => current_user.id)
  	aFile = File.open( "config.txt", "w")
    aFile.syswrite(current_user.id.to_s + ".txt," + current_user.id.to_s + "_extra.txt")
    aFile.close

  end


  def store


  	aFile = File.open( current_user.id.to_s + ".txt", "w")
	 
		@reviews=Review.where(name: current_user.id)
		@reviews.each do |review|
			aFile.puts(review.description)

		end
	
	aFile.close



	redirect_to backend_front_path
  end


  def crawl_delete
  	@reviews=Review.where(name: current_user.id)
  	@reviews.each do|rev|
  		rev.destroy
  	end
  	@status=Status.find_by(:id => current_user.id)
  	@status.file_write=0
  	@status.crawl=0
  	@status.save
  	redirect_to backend_front_path
  end



	#def process
	#	      @op= %x(cd .. &  ls )
	#end


	def display
		aFile=File.open("temp")
	end









end
