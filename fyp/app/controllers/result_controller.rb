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

	def selected
		Dir.chdir "C:\\Users\\nach\\Documents\\NetBeansProjects"
	  	aFile = File.open( "overall.txt", "r")
	 	aFile.each_line do |line|
    	@overall=line.to_f
   	end

	end


end
