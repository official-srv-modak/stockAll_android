""" This is to do a Google search
AUTHOR - Sourav Modak
DATE - 23-11-2020
India
"""



from googlesearch import search
from urllib.request import Request, urlopen
import urllib.request
import re, sys
from datetime import date
from html_table_parser import *

nifty_file = "nifty_companies.txt"

def get_table_data(URL):
    """ This is to get the tabular data from the websites"""

    def url_get_contents(url):
        print("Requesting information to... "+url)
        req = urllib.request.Request(url=url)
        f = urllib.request.urlopen(req)
        return f.read()

    xhtml = url_get_contents(URL).decode('utf-8')
    print("Success!!! Parsing")
    p = HTMLTableParser()
    p.feed(xhtml)
    return (p.tables[1])
    """print("\n\nPANDAS DATAFRAME\n")
    print(pd.DataFrame(p.tables[1]))"""

def get_companies_table(query):
    """ This will just use google search and get the tabular from first and only first search result. The problem here
    is if the query is not proper enough for the google servers to generate the tabular search result, you are doomed"""

    output = search(query, lang='en', num=10, stop=10, pause=2)
    for url in output:
        try:
            table = get_table_data(url)  # filter on the table variable
            return table
        except:
            print(sys.exc_info()[0])
            print("Sorry some error in the url... Trying another")
            continue

def write_to_file(output_list_string):

    file_handle = open(nifty_file, "r+")

    file_handle.write(str(date.today()) + "\n")
    output_list = output_list_string.split(" NEXTLINE ")
    for line in output_list:
        file_handle.write(line+"\n")
    file_handle.close

def get_nifty_companies():
    """ Return the list of all the nifty companies"""

    table = get_companies_table("nifty companies")
    #tuple_cnt = 0
    """for tuple in table[0]:
        if re.search("name", tuple) or re.search("NAME", tuple) or re.search("Name", tuple):
            break
        tuple_cnt += 1"""
    list_of_companies = ""
    for row in table:
        for tuple in row:
            list_of_companies += tuple + "\t"
        list_of_companies += " NEXTLINE "
    # print(list_of_companies)

    return list_of_companies



def do_search(company_name):
    query = "news of "+str(company_name)
    urlOut = ""
    output = search(query, lang='en', num=10, stop=10, pause=2)
    for url in output:
        urlOut += str(url)+" NEXTLINE "

    return str(urlOut)

def get_number_of_days(date1):
    """ Get number of days that has been passed from past dates wrt to today"""

    d0 = date1
    d1 = date.today()
    delta = d1 - d0
    return str(delta)

#print(get_nifty_companies())