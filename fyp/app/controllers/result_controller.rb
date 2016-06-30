class ResultController < ApplicationController
  def front
  	Dir.chdir "C:\\Users\\nach\\Documents\\NetBeansProjects"
  	aFile = File.open( "overall.txt", "r")
 	aFile.each_line do |line|
    	@overall=line.to_f
   	end
	aFile.close 	
 	@overall=@overall*100

	data_table = GoogleVisualr::DataTable.new
	option = { width: 600, height: 400, title: 'Overall Product Perception' }

	data_table.new_column('string', 'Perception' )
	data_table.new_column('number', 'Positive')
	data_table.new_column('number', 'Negative')

	# Add Rows and Values
	data_table.add_rows([
	    ['Sentiment', @overall, ((100).to_f - @overall)],

	
	])

	@chart = GoogleVisualr::Interactive::BarChart.new(data_table, option)

	@feature_file=File.open("feature.txt","r")


	if (params[:param1])
		param1 = params[:param1]
		param2 = params[:param2]
		@feature=param2.to_f * 100
		data_table = GoogleVisualr::DataTable.new
		option = { width: 600, height: 400, title: param1 }

		data_table.new_column('string', 'Perception' )
		data_table.new_column('number', 'Positive')
		data_table.new_column('number', 'Negative')

		# Add Rows and Values
		data_table.add_rows([
		    ['Sentiment', @feature, ((100).to_f - @feature)],

		
		])

		@fea = GoogleVisualr::Interactive::BarChart.new(data_table, option)


	end

  end


	def view


		data_table = GoogleVisualr::DataTable.new
		# Add Column Headers


		data_table.new_column('string', 'Feature' )
		data_table.new_column('number', 'Positive Sentiment')
		

		# Add Rows and Values
		@size=0
		File.open("feature_sort.txt","r").each_line do |line| 
		 	line.split(",").each do |x|

				@name=@percentage 
				@percentage=x 
				@size=@size+1
			 end 
			 data_table.add_rows([
		    [@name, 100.to_f*@percentage.to_f]
		])
		end
		
		
		option = { width: 1700, height: @size*18, title: 'Feature Perception' }
		@chart = GoogleVisualr::Interactive::BarChart.new(data_table, option)


	



	end




	def sort1
		Dir.chdir "C:\\Users\\nach\\Documents\\NetBeansProjects"

		@size=0
		@sorted=File.open("feature_sort.txt","r")
		@sorted.each_line do |x|
			@size=@size+1
		end
		@part=@size/3


	end

end
